package com.yantur.newsapp.data.export

import com.yantur.newsapp.domain.export.ExportRepository

class ExportRepositoryImpl(private val directoryService: ExportService) : ExportRepository {
    override fun exportToFile(objectToParse: Any, filePath: String): Unit =
        directoryService.export(objectToParse, filePath)
}