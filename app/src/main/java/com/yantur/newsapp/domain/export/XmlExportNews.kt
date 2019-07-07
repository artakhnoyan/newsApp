package com.yantur.newsapp.domain.export

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "articles", strict = false)
data class XmlExportNews(
    @field:ElementList(entry = "item", inline = true)
    var articles: List<XmlExportItem> = mutableListOf()
)

data class XmlExportItem(
    @field:Element(name = "title")
    var title: String = "",
    @field:Element(name = "description")
    var description: String = "",
    @field:Element(name = "imageUrl")
    var imageUrl: String = "",
    @field:Element(name = "link")
    var url: String = "",
    @field:Element(name = "date")
    var date: String = ""
)