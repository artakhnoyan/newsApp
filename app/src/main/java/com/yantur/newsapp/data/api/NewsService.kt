package com.yantur.newsapp.data.api

import com.yantur.newsapp.data.news.apinews.ApiNews
import com.yantur.newsapp.data.news.bbc.BBCNews
import retrofit2.http.GET

interface NewsService {

    @GET("http://feeds.bbci.co.uk/news/video_and_audio/technology/rss.xml")
    @Xml
    suspend fun fetchBBCNews(): BBCNews

    @GET("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=97ba815035ae4381b223377b2df975ab")
    @Json
    suspend fun fetchNewsApiNews(): ApiNews
}