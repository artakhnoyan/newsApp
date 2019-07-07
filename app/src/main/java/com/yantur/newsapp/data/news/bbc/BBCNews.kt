package com.yantur.newsapp.data.news.bbc

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.text.SimpleDateFormat
import java.util.*

@Root(name = "rss", strict = false)
data class BBCNews(
    @field:Element(name = "channel")
    var channel: Channel = Channel()
) {
    @Root(name = "channel", strict = false)
    data class Channel(
        @field:Element(name = "image")
        var image: Image = Image(),
        @field:ElementList(entry = "item", inline = true)
        var articles: List<BBCNewsItem> = mutableListOf()
    ) {
        @Root(name = "image", strict = false)
        data class Image(
            @field:Element(name = "url")
            var url: String = ""
        )
    }
}

@Root(name = "item", strict = false)
data class BBCNewsItem(
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "link")
    var url: String = "",
    @field:Element(name = "pubDate")
    var publishDate: String = ""
) {
    companion object {
        const val DATE_FORMAT: String = "EEE, dd MMM yyyy HH:mm:ss 'GMT'"
    }

    fun getDate(): Date {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US)
        return dateFormat.parse(publishDate)
    }
}