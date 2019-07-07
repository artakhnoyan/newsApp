package com.yantur.newsapp.domain.news

import com.yantur.newsapp.data.news.News

interface NewsRepository {
    suspend fun fetchNews(): List<News>
}