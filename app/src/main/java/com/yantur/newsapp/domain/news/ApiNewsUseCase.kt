package com.yantur.newsapp.domain.news

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.apinews.ApiNewsRepository

class ApiNewsUseCase(private val apiNewsRepository: ApiNewsRepository) : NewsUseCase {

    override suspend operator fun invoke(): List<News> = apiNewsRepository.fetchNews()
}