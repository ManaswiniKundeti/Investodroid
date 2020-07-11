package com.manu.investodroid

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manu.investodroid.view.ui.main.StocksListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockListFragmentTest {

    @Test
    fun test_start_state() {
        launchFragmentInContainer<StocksListFragment>()

        onView(withId(R.id.stock_refresh_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.stocksList)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun test_stocks_loaded() {
        launchFragmentInContainer {
            StocksListFragment().also { fragment ->
                IdlingRegistry.getInstance().register(fragment.getIdlingResource())
            }
        }

        onView(withId(R.id.stock_refresh_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.stocksList)).check(RecyclerViewItemCountAssertion(14052))
    }

    @Test
    fun test_search_stocks() {
        launchFragmentInContainer {
            StocksListFragment().also { fragment ->
                IdlingRegistry.getInstance().register(fragment.getIdlingResource())
            }
        }

        onView(withId(R.id.stock_refresh_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.stocksList)).check(RecyclerViewItemCountAssertion(14052))

        onView(withId(R.id.search_edit_text)).perform(typeText("face"))
        onView(withId(R.id.stocksList)).check(RecyclerViewItemCountAssertion(4))
    }
}