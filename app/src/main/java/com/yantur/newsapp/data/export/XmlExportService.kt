package com.yantur.newsapp.data.export

import com.yantur.newsapp.data.export.directory.DirectoryService
import org.simpleframework.xml.Serializer
import java.io.File


class XmlExportService(
    private val serializer: Serializer,
    private val directoryService: DirectoryService
) : ExportService {
    override fun export(objectToParse: Any, filePath: String): Unit =
        serializer.write(objectToParse, File(directoryService.getDirectory(filePath)))
}