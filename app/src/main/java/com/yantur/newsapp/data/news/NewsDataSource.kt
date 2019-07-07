package com.yantur.newsapp.data.news

interface NewsDataSource<T> {
    suspend fun fetchNews(): T
}