package com.yantur.newsapp.presenter.news

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.yantur.newsapp.BR
import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.domain.DataStoreUseCase
import com.yantur.newsapp.domain.export.*
import com.yantur.newsapp.domain.news.NewsUseCase
import com.yantur.newsapp.presenter.BaseViewModel
import com.yantur.newsapp.presenter.Event
import com.yantur.newsapp.presenter.State
import com.yantur.newsapp.presenter.map
import java.util.*

class NewsViewModel(
    private val newsSources: List<NewsUseCase>,
    private val jsonExportUseCase: ExportUseCase,
    private val xmlExportUseCase: ExportUseCase,
    private val newsDataStoreUseCase: DataStoreUseCase<List<News>>
) : BaseViewModel(), NewsItemCallback {
    private val _newsItem: MutableLiveData<Event<NewsItem>> = MutableLiveData()
    private val _news: MutableLiveData<State<List<NewsItem>>> = MediatorLiveData()
    private val _loading: MediatorLiveData<Boolean> = MediatorLiveData()
    private val _jsonExport: MutableLiveData<State<Event<Unit>>> = MutableLiveData()
    private val _xmlExport: MutableLiveData<State<Event<Unit>>> = MutableLiveData()

    val newsComplete: LiveData<List<NewsItem>?> = _news.map { (it as? State.Complete)?.data }
    val newsError: LiveData<Exception?> = _news.map { (it as? State.Error)?.exception }
    val progressBar: LiveData<Boolean> = _loading

    val jsonExportComplete: LiveData<Event<Unit>?> = _jsonExport.map { (it as? State.Complete)?.data }
    val xmlExportComplete: LiveData<Event<Unit>?> = _xmlExport.map { (it as? State.Complete)?.data }
    val newsItem: LiveData<Event<NewsItem>> = _newsItem

    var lastExportClicked: ExportType = ExportType.JSON

    var isLoading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }
        @Bindable
        get() = field

    var isError: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
        }
        @Bindable
        get() = field

    init {
        _loading.addSource(_news.map { it == State.Loading }) {
            isLoading = it
        }
        _loading.addSource(_jsonExport.map { it == State.Loading }) {
            isLoading = it
        }
        _loading.addSource(_xmlExport.map { it == State.Loading }) {
            isLoading = it
        }
        retrieveAllNews()
    }

    fun refresh() {
        isError = false
        retrieveAllNews()
    }

    fun retrieveAllNews(): Unit = execute(_news) {
        val sortedNews = newsSources
            .map { executeAsync { it() } }
            .flatMap { it.await() }
            .sortedByDescending {
                it.publishDate
            }

        sortedNews.forEach {
            println("#art: ${it.imageUrl}: " + it.publishDate)
        }
        executeAsync { newsDataStoreUseCase.storeData(sortedNews) }

        sortedNews.map { it.mapToNewsItem() }
    }


    fun exportAsJson(): Unit = execute(_jsonExport) {
        val jsonExportNews = JsonExportNews(
            newsDataStoreUseCase
                .retrieveData()
                .map { it.mapToJsonExportNews() }
        )
        Event(jsonExportUseCase(jsonExportNews, JSON_PATH))

    }

    fun exportAsXml(): Unit = execute(_xmlExport) {
        val xmlExportNews = XmlExportNews(
            newsDataStoreUseCase
                .retrieveData()
                .map { it.mapToXmlExportNews() }
        )
        Event(xmlExportUseCase(xmlExportNews, XML_PATH))
    }

    fun continueExport() {
        when (lastExportClicked) {
            is ExportType.JSON -> exportAsJson()
            is ExportType.XMl -> exportAsXml()
        }
    }

    override fun onNewsItemClick(view: View, newsItem: NewsItem) {
        _newsItem.value = Event(newsItem)
    }

    override fun onRefreshButtonClick() {
        retrieveAllNews()
    }

    private fun News.mapToNewsItem(): NewsItem =
        NewsItem(title, description, imageUrl, url)

    private fun News.mapToXmlExportNews(): XmlExportItem =
        XmlExportItem(title, description, imageUrl, url, Date(publishDate).toString())

    private fun News.mapToJsonExportNews(): JsonExportNewsItem =
        JsonExportNewsItem(title, description, imageUrl, url, Date(publishDate).toString())

    companion object {
        private const val PATH: String = "newsApp/"
        const val JSON_PATH: String = "${PATH}news.json"
        const val XML_PATH: String = "${PATH}news.xml"
    }
}
