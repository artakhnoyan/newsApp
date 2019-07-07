package com.yantur.newsapp.data.export

interface ExportService {
    fun export(objectToParse: Any, filePath: String)
}