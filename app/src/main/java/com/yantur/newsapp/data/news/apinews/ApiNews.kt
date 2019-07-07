package com.yantur.newsapp.data.news.apinews

import java.util.*


data class ApiNews(
    val articles: List<ApiNewsItem>
)

data class ApiNewsItem(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date
)