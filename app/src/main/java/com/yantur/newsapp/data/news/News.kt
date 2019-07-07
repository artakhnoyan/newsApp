package com.yantur.newsapp.data.news

data class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val url: String,
    val publishDate: Long
)