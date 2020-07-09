package com.manu.investodroid

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.manu.investodroid.view.adapter.StocksListItemViewHolder
import com.manu.investodroid.view.ui.main.MainActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Description
import org.hamcrest.Matcher

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true,true)

    @Test
    fun test_main_start_state(){
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        //onView(withId(R.id.stock_refresh_layout)).check(matches(SwipeRefreshLayoutMatchers.isRefreshing()))
        onView(withId(R.id.stocksList)).check(RecyclerViewItemCountAssertion(0))
        onView(withId(R.id.stocksList))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).check(matches(isDisplayed()))
    }

    @Test
    fun test_stocks_List_recycler_view_item_click() {
        onView(withId(R.id.stocksList))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).perform(RecyclerViewActions.actionOnItemAtPosition<StocksListItemViewHolder>(0, click()))
    }

    @Test
    fun test_stock_list_recycler_view_scroll(){
        val recyclerView : RecyclerView =  activityRule.activity.findViewById(R.id.stocksList)
        val itemCount : Int = recyclerView.adapter!!.itemCount

        onView(withId(R.id.stocksList))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).perform(RecyclerViewActions.scrollToPosition<StocksListItemViewHolder>(itemCount-1))
    }

    @Test
    fun test_stock_list_recycler_item_view(){
        onView(withId(R.id.stocksList))
            .inRoot(RootMatchers.withDecorView(
                Matchers.`is`(activityRule.activity.window.decorView)
            )).check(matches(CustomViewMatcher.atPosition(0, Matchers.allOf(
                withId(R.id.item_stock_constraint_layout), isDisplayed()
            ))))
    }

}
