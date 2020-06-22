package com.example.callingkotlinrepositories.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.callingkotlinrepositories.R

fun Context.openActivity(destination: Class<*>) {
    startActivity(Intent(this, destination))
}

fun ImageView.loadPicture(url: String) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.github)
        .error(R.drawable.github)
        .into(this)
}