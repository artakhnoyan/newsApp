package com.yantur.newsapp.domain.news

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.apinews.ApiNewsRepository
import com.yantur.newsapp.data.news.bbc.BBCNewsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class BBCNewsUseCaseTest {

    @Test
    fun `verifying bbc repo invoke`() {
        val bbcNewsRepository = mockk<BBCNewsRepository>()


        val input = mutableListOf<News>()
        repeat(10) {
            input.add(createNews(it.toLong()))
        }

        every { runBlocking { bbcNewsRepository.fetchNews() } } returns input

        val bbcNewsUseCase: NewsUseCase = BBCNewsUseCase(
            bbcNewsRepository
        )

        val data = runBlocking { bbcNewsUseCase() }

        Assert.assertEquals(data, input)

    }

    @Test
    fun `verifying api repo invoke`() {
        val apiNewsRepository = mockk<ApiNewsRepository>()


        val input = mutableListOf<News>()
        repeat(10) {
            input.add(createNews(it.toLong()))
        }

        every { runBlocking { apiNewsRepository.fetchNews() } } returns input

        val apiNewsUseCase: NewsUseCase = ApiNewsUseCase(
            apiNewsRepository
        )

        val data = runBlocking { apiNewsUseCase() }

        Assert.assertEquals(data, input)
    }
}


internal fun createNews(date: Long) =
    News("title", "description", "imageUrl", "url", date)

