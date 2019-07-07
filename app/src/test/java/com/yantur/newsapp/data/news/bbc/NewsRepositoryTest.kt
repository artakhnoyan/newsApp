package com.yantur.newsapp.data.news.bbc

import com.yantur.newsapp.data.news.News
import com.yantur.newsapp.data.news.apinews.ApiNews
import com.yantur.newsapp.data.news.apinews.ApiNewsDataSource
import com.yantur.newsapp.data.news.apinews.ApiNewsItem
import com.yantur.newsapp.data.news.apinews.ApiNewsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class NewsRepositoryTest {

    @Test
    fun `verifying bbc news mapping`() {
        val bbcDataSource = mockk<BBCDataSource>()

        val bbcNewsItems = mutableListOf<BBCNewsItem>()

        val newsItems = mutableListOf<News>()
        repeat(10) {
            bbcNewsItems.add(createBBCNewsItem(it.toString()))
            newsItems.add(createForBBCNews(it.toString()))
        }

        val bbcNews = createBBCNews(bbcNewsItems)

        every { runBlocking { bbcDataSource.fetchNews() } } returns bbcNews

        val bbcNewsRepository = BBCNewsRepository(bbcDataSource)

        val data = runBlocking { bbcNewsRepository.fetchNews() }

        Assert.assertArrayEquals(data.toTypedArray(), newsItems.toTypedArray())
    }

    @Test
    fun `verifying api news mapping`() {
        val apiDataSource = mockk<ApiNewsDataSource>()

        val apiNewsItems = mutableListOf<ApiNewsItem>()

        val newsItems = mutableListOf<News>()
        repeat(10) {
            apiNewsItems.add(createApiNewsItem(it.toString()))
            newsItems.add(createForApiNews(it.toString()))
        }

        val apiNews = createAPiNews(apiNewsItems)

        every { runBlocking { apiDataSource.fetchNews() } } returns apiNewsItems

        val bbcNewsRepository = ApiNewsRepository(apiDataSource)

        val data = runBlocking { bbcNewsRepository.fetchNews() }


        Assert.assertArrayEquals(data.toTypedArray(), newsItems.toTypedArray())
    }


    private val bbcDate: String = "Sun, 07 Jul 2019 17:41:53 GMT"

    private fun createForBBCNews(title: String) =
        News(
            title,
            "description",
            "imageUrl",
            "url",
            SimpleDateFormat(BBCNewsItem.DATE_FORMAT, Locale.US).parse(bbcDate).time
        )

    private fun createBBCNews(list: List<BBCNewsItem>): BBCNews =
        BBCNews(BBCNews.Channel(BBCNews.Channel.Image("imageUrl"), list))

    private fun createBBCNewsItem(title: String): BBCNewsItem =
        BBCNewsItem(title, "description", "url", bbcDate)

    private val apiDate: Date = Date(123L)

    private fun createForApiNews(title: String) =
        News(
            title,
            "description",
            "imageUrl",
            "url",
            apiDate.time
        )

    private fun createAPiNews(list: List<ApiNewsItem>): ApiNews = ApiNews(list)

    private fun createApiNewsItem(title: String): ApiNewsItem =
        ApiNewsItem(title, "description", "url", "imageUrl", apiDate)

}
