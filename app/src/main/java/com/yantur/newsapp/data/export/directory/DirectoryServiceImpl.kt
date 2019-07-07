package com.yantur.newsapp.data.export.directory

import java.io.File

class DirectoryServiceImpl(private val externalFileDirectory: File) :
    DirectoryService {
    override fun getDirectory(path: String): String {
        val dir = File(externalFileDirectory, path)
        val parent = dir.parentFile
        if (!parent.exists()) {
            parent.mkdirs()
        }
        if (dir.exists()) {
            dir.delete()
        }
        dir.createNewFile()
        return dir.path
    }
}