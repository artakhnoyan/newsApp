package com.yantur.newsapp.data.api

import retrofit2.Retrofit


interface APIClient {
    fun <T> createService(clazz: Class<T>): T
}

class RetrofitClient : APIClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://google.com") //cannot init without base url
        .addConverterFactory(NewsConverterFactory())
        .build()

    override fun <T> createService(clazz: Class<T>): T = retrofit.create(clazz)

}
