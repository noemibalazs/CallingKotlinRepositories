package com.example.callingkotlinrepositories.helper

import android.os.SystemClock
import android.view.View
import java.util.*
import kotlin.math.abs

abstract class DebounceClickListener(private val timeInMillis: Long = 900) : View.OnClickListener {

    private val myMap: MutableMap<View, Long>

    abstract fun onDebounce(view: View)

    init {
        myMap = WeakHashMap<View, Long>()
    }

    override fun onClick(view: View) {
        val previousTime = myMap[view]
        val currentTime = SystemClock.uptimeMillis()
        myMap[view] = currentTime
        if (previousTime == null || abs(currentTime - previousTime.toLong()) > timeInMillis)
            onDebounce(view)
    }
}