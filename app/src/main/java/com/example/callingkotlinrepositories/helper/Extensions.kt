package com.example.callingkotlinrepositories.helper

import android.content.Context
import android.content.Intent

fun Context.openActivity(destination: Class<*>){
   startActivity(Intent(this, destination))
}
