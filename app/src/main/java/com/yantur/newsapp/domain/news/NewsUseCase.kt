package com.yantur.newsapp.domain.news

import com.yantur.newsapp.data.news.News
interface NewsUseCase {
    suspend operator fun invoke(): List<News>
}


