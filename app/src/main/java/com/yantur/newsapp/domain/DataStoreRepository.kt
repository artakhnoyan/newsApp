package com.yantur.newsapp.domain

interface DataStoreRepository<T> {
    fun retrieveData(): T
    fun storeData(data: T)
}