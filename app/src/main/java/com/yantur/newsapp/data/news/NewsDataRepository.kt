package com.yantur.newsapp.data.news

import com.yantur.newsapp.domain.DataStoreRepository

class NewsDataRepository : DataStoreRepository<List<News>> {
    private val storedData: MutableList<News> = mutableListOf()

    override fun retrieveData(): List<News> = storedData

    override fun storeData(data: List<News>) {
        storedData.clear()
        storedData.addAll(data)
    }

}