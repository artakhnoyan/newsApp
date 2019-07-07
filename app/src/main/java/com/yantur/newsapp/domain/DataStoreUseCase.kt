package com.yantur.newsapp.domain

interface DataStoreUseCase<T> {
    fun retrieveData(): T
    fun storeData(data: T)
}