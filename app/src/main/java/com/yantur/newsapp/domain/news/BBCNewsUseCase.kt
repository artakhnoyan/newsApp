package com.yantur.newsapp.domain.news

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.bbc.BBCNewsRepository

class BBCNewsUseCase(private val bbcNewsRepository: BBCNewsRepository) : NewsUseCase {

    override suspend operator fun invoke(): List<News> = bbcNewsRepository.fetchNews()
}