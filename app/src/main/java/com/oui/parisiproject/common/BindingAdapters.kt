package com.oui.parisiproject.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oui.parisiproject.R

@BindingAdapter("urlToImage")
fun urlToImage(view: ImageView, s: String?) {
    val options = RequestOptions.placeholderOf(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)
    Glide.with(view).setDefaultRequestOptions(options).load(s ?: "").into(view)
}