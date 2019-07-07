package com.yantur.newsapp.presenter.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import com.yantur.newsapp.R
import com.yantur.newsapp.databinding.ItemNewsBinding


class NewsAdapter(
    private val newsItemCallback: NewsItemCallback
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val news: MutableList<NewsItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = inflate<ItemNewsBinding>(
            LayoutInflater.from(parent.context), R.layout.item_news,
            parent, false
        )

        binding.newsItemCallback = newsItemCallback

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.newsItem = news[position]
    }

    override fun getItemCount(): Int = news.size

    fun setNews(data: List<NewsItem>) {
        news.clear()
        news.addAll(data)
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemNewsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemNewsBinding.root) {
        val binding: ItemNewsBinding = itemNewsBinding
    }

}