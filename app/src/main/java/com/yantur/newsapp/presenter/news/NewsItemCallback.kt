package com.yantur.newsapp.presenter.news

import android.view.View

interface NewsItemCallback {

    fun onNewsItemClick(view: View, newsItem: NewsItem)

    fun onRefreshButtonClick()
}