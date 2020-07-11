package com.manu.investodroid

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.manu.investodroid.view.adapter.StocksListItemViewHolder
import com.manu.investodroid.view.ui.main.MainActivity
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true,true)

    /**
     * Test for Favouriting and Unfavouriting a stock
     */
    @Test
    fun test_favorite_unfavorite_flow() {
        // Click Item to launch Stock Detail Activity
        onView(withId(R.id.stocksList))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).perform(RecyclerViewActions.actionOnItemAtPosition<StocksListItemViewHolder>(1, click()))

        // Click Add to Favourites Menu option
        onView(withId(R.id.favouriteMenuItem)).perform(click())

        Espresso.pressBack()

        // Open Favourites Fragment
        onView(withId(R.id.favouriteStocksFragment)).perform(
            click())

        // Verify only 1 favourite item is displayed
        onView(withId(R.id.empty_state_image_view)).check(matches(not((ViewMatchers.isDisplayed()))))
        onView(withId(R.id.empty_state_text_view)).check(matches(not((ViewMatchers.isDisplayed()))))
        onView(withId(R.id.favouriteStocksList)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.favouriteStocksList)).check(RecyclerViewItemCountAssertion(1))

        // Unfavorite previously favorites item to clear data
        onView(withId(R.id.favouriteStocksList))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).perform(RecyclerViewActions.actionOnItemAtPosition<StocksListItemViewHolder>(0, click()))

        onView(withId(R.id.favouriteMenuItem)).perform(click())
    }
}
