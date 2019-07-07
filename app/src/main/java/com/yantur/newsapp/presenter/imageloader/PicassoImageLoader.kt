package com.yantur.newsapp.presenter.imageloader

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("newsUrl")
fun loadImageUrl(view: ImageView, url: String) {
    Picasso.with(view.context)
        .load(url)
        .into(view)
}
