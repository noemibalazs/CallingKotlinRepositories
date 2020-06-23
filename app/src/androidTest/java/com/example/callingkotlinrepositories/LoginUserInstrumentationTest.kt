package com.example.callingkotlinrepositories

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.callingkotlinrepositories.loginuser.LoginUserActivity
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginUserInstrumentationTest {

    @get:Rule
    val rule = ActivityTestRule(LoginUserActivity::class.java)

    @Test
    fun appContextTest(){
        val context = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.example.callingkotlinrepositories.test", context.packageName)
    }

    @Test
    fun testToolbarIsVisible() {

        Espresso.onView(withId(R.id.iv_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_sign_in))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText(R.string.txt_sign_in_to_GitHub))))
    }

}