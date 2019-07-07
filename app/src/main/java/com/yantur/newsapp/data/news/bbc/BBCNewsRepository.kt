package com.yantur.newsapp.data.news.bbc

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.NewsDataSource
import com.yantur.newsapp.domain.news.NewsRepository

class BBCNewsRepository(private val bbcDataSource: NewsDataSource<BBCNews>) : NewsRepository {

    override suspend fun fetchNews(): List<News> {
        val bbcNews = bbcDataSource.fetchNews()
        return bbcNews.channel.articles.map { it.mapToNews(bbcNews.channel.image.url) }
    }

    private fun BBCNewsItem.mapToNews(imageUrl: String): News =
        News(title, description, imageUrl, url, getDate().time)
}

