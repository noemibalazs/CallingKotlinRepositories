package com.example.callingkotlinrepositories

import androidx.appcompat.widget.MenuPopupWindow.MenuDropDownListView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.callingkotlinrepositories.data.Repository
import com.example.callingkotlinrepositories.repository.RepositoryActivity
import com.example.callingkotlinrepositories.repository.RepositoryAdapter
import com.example.callingkotlinrepositories.repository.RepositoryVH
import com.example.callingkotlinrepositories.repository.RepositoryViewModel
import io.mockk.mockk
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RepositoryActivityInstrumentationTest {

    @get: Rule
    val activityTestRule = ActivityTestRule(RepositoryActivity::class.java)

    private lateinit var viewModel: RepositoryViewModel

    @Before
    fun setUp() {
        viewModel = mockk<RepositoryViewModel>()
    }

    @Test
    fun signOutClickedTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onData(CoreMatchers.anything())
            .inRoot(RootMatchers.isPlatformPopup())
            .inAdapterView(CoreMatchers.instanceOf(MenuDropDownListView::class.java))
            .atPosition(1)
            .perform(click())
    }

    @Test
    fun clickOnRecycleViewItemTest() {
        val repositoryList =
            mutableListOf(Repository(12, "kotlin", "JetBrain/kotlin", "Lorem ipsum dolor sit amet"))
        val adapter = RepositoryAdapter(viewModel)
        adapter.submitList(repositoryList)

        activityTestRule.activity.runOnUiThread {
            activityTestRule.activity.findViewById<RecyclerView>(R.id.rv_repository).adapter =
                adapter
        }
        onView(withId(R.id.rv_repository)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_repository)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RepositoryVH>(
                0,
                click()
            )
        )
    }
}