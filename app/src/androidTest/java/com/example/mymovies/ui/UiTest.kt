package com.example.mymovies.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.example.mymovies.R
import com.example.mymovies.data.server.TheMovieDb
import com.example.mymovies.ui.main.MainActivity
import com.example.mymovies.ui.utils.fromJson
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.test.KoinTest
import org.koin.test.get

class UiTest : KoinTest  {

    private val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val testRule: RuleChain = RuleChain
        .outerRule(mockWebServerRule)
        .around(
            GrantPermissionRule.grant(
                "android.permission.ACCESS_COARSE_LOCATION"
            )
        )
        .around(ActivityScenarioRule(MainActivity::class.java))

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("popularmovies.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<TheMovieDb>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun recyclerIsPopulatedWithCorrectData(){
        Espresso.onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(19))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Xtreme"))))
    }

    @Test
    fun clickAMovieNavigatesToDetail() {
        Espresso.onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        Espresso.onView(withId(R.id.movieDetailToolbar))
            .check(matches(ViewMatchers.hasDescendant(ViewMatchers.withText("Luca"))))

    }

    @Test
    fun clickBackArrowDetailNavigatesBackToMain(){
        Espresso.onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        pressBack()

        Espresso.onView(withId(R.id.toolbar)).check(matches(isDisplayed()))

    }
}