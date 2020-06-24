package com.example.callingkotlinrepositories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.callingkotlinrepositories.loginuser.LoginUserActivity
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginUserActivityInstrumentationTest {

    @get:Rule
    val rule = ActivityTestRule(LoginUserActivity::class.java)

    @Test
    fun appContextTest() {
        val context = InstrumentationRegistry.getInstrumentation().context
        assertEquals("com.example.callingkotlinrepositories.test", context.packageName)
    }

    @Test
    fun containerLayoutItemsTest() {
        onView(withId(R.id.iv_github_logo)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_sign_in)).check(matches((withText(R.string.txt_sign_in_to_GitHub))))
        onView(withId(R.id.tv_sign_in)).check(matches((EspressoHelper.checkTextSize(18f))))
        onView(withId(R.id.v_first_delimiter)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_username)).check(matches(isDisplayed()))
            .check(matches(withText(R.string.txt_username_or_email)))
        onView(withId(R.id.tv_password)).check(matches(isDisplayed()))
            .check(matches(withText(R.string.txt_password)))
        onView(withId(R.id.et_username)).check(matches(isDisplayed()))
            .perform(typeText("kotliner"), closeSoftKeyboard())
        onView(isRoot()).perform(EspressoHelper.waitFor(600L))
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
            .perform(typeText("kotlinsavy"), closeSoftKeyboard())
        onView(withId(R.id.bt_sign_in)).check(matches(isDisplayed())).perform(click())
    }
}