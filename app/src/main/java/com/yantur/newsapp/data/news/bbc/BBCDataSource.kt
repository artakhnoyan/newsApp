package com.yantur.newsapp.data.news.bbc

import com.yantur.newsapp.data.api.NewsService
import com.yantur.newsapp.data.news.NewsDataSource

class BBCDataSource(private val service: NewsService) : NewsDataSource<BBCNews> {
    override suspend fun fetchNews(): BBCNews = service.fetchBBCNews()
}
