package com.example.callingkotlinrepositories.utils

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.callingkotlinrepositories.R
import com.orhanobut.logger.Logger
import org.koin.core.logger.KOIN_TAG

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

fun TextView.setSpannableText(text: String) {
    val spannableString = SpannableString(text)
    val index = text.indexOf(":")
    spannableString.setSpan(
        RelativeSizeSpan(1.1f),
        0,
        index + 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    spannableString.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        index + 1,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = spannableString
}