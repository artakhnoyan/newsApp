package com.yantur.newsapp.data.export.directory

interface DirectoryService {
    fun getDirectory(path: String): String
}