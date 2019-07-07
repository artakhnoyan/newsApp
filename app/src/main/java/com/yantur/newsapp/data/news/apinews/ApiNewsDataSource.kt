package com.yantur.newsapp.data.news.apinews

import com.yantur.newsapp.data.api.NewsService
import com.yantur.newsapp.data.news.NewsDataSource

class ApiNewsDataSource(private val service: NewsService) : NewsDataSource<List<ApiNewsItem>> {
    override suspend fun fetchNews(): List<ApiNewsItem> = service.fetchNewsApiNews().articles
}
