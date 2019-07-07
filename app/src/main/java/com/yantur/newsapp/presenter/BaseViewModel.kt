package com.yantur.newsapp.presenter

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope, Observable {

    private var job: Job = SupervisorJob()
    private val backgroundDispatcher: CoroutineContext = Dispatchers.IO
    private val foregroundDispatcher: CoroutineContext = Dispatchers.Main

    override val coroutineContext: CoroutineContext
        get() = job + foregroundDispatcher

    protected fun <T> executeAsync(block: suspend () -> T): Deferred<T> = async(job) { block() }

    protected fun <T> execute(state: MutableLiveData<State<T>>, block: suspend () -> T) {
        launch {
            try {
                state.value = State.Loading
                val result = withContext(backgroundDispatcher) { block() }
                if (job.isActive) {
                    state.value = State.Loading
                    state.value = State.Complete(result)
                }
            } catch (exception: Exception) {
                state.value = State.Loading
                state.value = State.Error(exception)
            }
        }
    }

    protected fun cancel() {
        job.apply {
            cancelChildren()
            cancel()
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}