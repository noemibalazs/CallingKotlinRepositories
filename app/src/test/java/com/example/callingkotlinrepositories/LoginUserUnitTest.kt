package com.example.callingkotlinrepositories

import com.example.callingkotlinrepositories.loginuser.LoginUserActivity
import junit.framework.Assert.assertEquals

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@org.robolectric.annotation.Config(manifest = Config.NONE)
class LoginUserUnitTest {

    private var loginUserActivity: LoginUserActivity? = null

    @Before
    fun create() {
        loginUserActivity =
            Robolectric.buildActivity(LoginUserActivity::class.java).create().resume().get()
    }

    @Test
    fun appContextTest() {
        val context = RuntimeEnvironment.systemContext
        assertEquals("android", context.packageName)
    }
}