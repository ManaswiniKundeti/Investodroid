package com.manu.investodroid

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.manu.investodroid.view.ui.main.MainActivity
import com.manu.investodroid.view.ui.stock_detail.StockDetailActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockDetailActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(StockDetailActivity::class.java, false, false)

    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra(StockDetailActivity.ARG_STOCK_SYMBOL, INTENT_SYMBOL)
        activityRule.launchActivity(intent)

        IdlingRegistry.getInstance().register(activityRule.activity.getIdlingResource())
    }

    @Test
    fun test_stock_detail_start_state(){
        onView(withId(R.id.company_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.price))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.price))
            .check(ViewAssertions.matches(withText(SECOND_COMPANY_PRICE)))
        onView(withId(R.id.about_text_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.about_text_view))
            .check(ViewAssertions.matches(withText(ABOUT_TEXT_VIEW)))
        onView(withId(R.id.ceo_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.ceo_name))
            .check(ViewAssertions.matches(withText(SECOND_CEO_NAME)))
        onView(withId(R.id.headquarters_name))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.headquarters_name))
            .check(ViewAssertions.matches(withText(SECOND_HEADQUARTERS_NAME)))
        onView(withId(R.id.employees))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.employees))
            .check(ViewAssertions.matches(withText(SECOND_EMPLOYEE_COUNT)))

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(activityRule.activity.getIdlingResource())
    }

}