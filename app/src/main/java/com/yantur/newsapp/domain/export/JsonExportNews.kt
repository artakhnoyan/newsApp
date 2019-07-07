package com.yantur.newsapp.domain.export

data class JsonExportNews(val articles: List<JsonExportNewsItem>)

data class JsonExportNewsItem(
    val title: String,
    val description: String,
    val imageUrl: String,
    val url: String,
    val date: String
)