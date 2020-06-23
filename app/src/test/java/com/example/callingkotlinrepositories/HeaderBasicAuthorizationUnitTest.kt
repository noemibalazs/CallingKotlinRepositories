package com.example.callingkotlinrepositories

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@org.robolectric.annotation.Config(manifest = Config.NONE)
class HeaderBasicAuthorizationUnitTest {

    @Test
    fun headerBasicAuthorizationTextTest() {
        val user = "kotlinsavy"
        val password = "KotlinIsland"
        val result = Base64.getEncoder().encodeToString(("$user:$password").toByteArray())
        val token = "a290bGluc2F2eTpLb3RsaW5Jc2xhbmQ="
        assertEquals(result, token)
    }

}