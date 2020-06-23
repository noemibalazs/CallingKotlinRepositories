package com.example.callingkotlinrepositories

import com.example.callingkotlinrepositories.helper.DataManager
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@org.robolectric.annotation.Config(manifest= Config.NONE)
class DataMangerUnitTest {

    private var dataManager:DataManager ?= null
    private var repositoryID = 777

    @Before
    fun createDataManager() {
        dataManager = DataManager(RuntimeEnvironment.application.applicationContext)
    }

    @Test
    fun repositoryIdShouldPassedTest(){
        dataManager?.saveRepositoryId(repositoryID)
        val idResult = dataManager?.getRepositoryId()
        assertEquals(idResult, repositoryID)
    }
}