package com.yantur.newsapp.presenter.news

sealed class ExportType {
    object JSON : ExportType()
    object XMl : ExportType()
}