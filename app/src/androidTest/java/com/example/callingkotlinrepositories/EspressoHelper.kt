package com.example.callingkotlinrepositories

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Description
import org.hamcrest.Matcher

class EspressoHelper {

    companion object {

        fun waitFor(milliSeconds: Long): ViewAction {
            return object : ViewAction {
                override fun getDescription(): String {
                    return "Wait for $milliSeconds"
                }

                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun perform(uiController: UiController?, view: View?) {
                    uiController?.loopMainThreadForAtLeast(milliSeconds)
                }
            }
        }

        fun checkTextSize(expectedSize: Float): Matcher<View> {
            return object : BoundedMatcher<View, View>(View::class.java) {
                override fun describeTo(description: Description?) {
                    description?.appendValue(expectedSize)
                }

                override fun matchesSafely(item: View?): Boolean {
                    item?.let {
                        if (!(item is TextView))
                            return false
                    }

                    val textView = item as TextView
                    val pixels = textView.textSize
                    val sizeToCompare = pixels / textView.resources.displayMetrics.scaledDensity
                    return sizeToCompare.compareTo(expectedSize) == 0
                }
            }
        }
    }
}