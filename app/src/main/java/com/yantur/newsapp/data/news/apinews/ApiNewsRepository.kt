package com.yantur.newsapp.data.news.apinews

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.NewsDataSource
import com.yantur.newsapp.domain.news.NewsRepository

class ApiNewsRepository(private val newsApiDataSource: NewsDataSource<List<ApiNewsItem>>) : NewsRepository {

    override suspend fun fetchNews(): List<News> {
        return newsApiDataSource.fetchNews().map { it.mapToNews() }
    }

    private fun ApiNewsItem.mapToNews(): News =
        News(title, description, urlToImage, url, publishedAt.time)
}