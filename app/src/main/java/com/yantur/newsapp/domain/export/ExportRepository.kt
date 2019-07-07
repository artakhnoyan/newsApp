package com.yantur.newsapp.domain.export

interface ExportRepository {
    fun exportToFile(objectToParse: Any, filePath: String)
}