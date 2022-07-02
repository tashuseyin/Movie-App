package com.example.moviecaseapp.common.extension

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecaseapp.R

fun ImageView.loadImageView(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.image_not_available)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}