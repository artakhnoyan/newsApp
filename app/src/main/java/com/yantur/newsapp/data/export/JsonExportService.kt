package com.yantur.newsapp.data.export

import com.google.gson.Gson
import com.yantur.newsapp.data.export.directory.DirectoryService
import java.io.FileWriter
import java.io.Writer

class JsonExportService(
    private val gson: Gson,
    private val directoryService: DirectoryService
) : ExportService {

    override fun export(objectToParse: Any, filePath: String) {
        var writer: Writer? = null
        try {
            writer = FileWriter(directoryService.getDirectory(filePath))
            gson.toJson(objectToParse, writer)
        } finally {
            writer?.apply {
                flush()
                close()
            }
        }
    }
}