package com.yantur.newsapp.domain.news

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.domain.DataStoreRepository
import com.yantur.newsapp.domain.DataStoreUseCase

class NewsDataStoreUseCase(private val repository: DataStoreRepository<List<News>>) :
    DataStoreUseCase<List<News>> {
    override fun retrieveData(): List<News> = repository.retrieveData()

    override fun storeData(data: List<News>): Unit = repository.storeData(data)

}