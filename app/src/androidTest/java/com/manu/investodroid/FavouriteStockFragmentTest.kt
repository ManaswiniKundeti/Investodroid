package com.manu.investodroid

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manu.investodroid.view.ui.main.FavouriteStocksFragment
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavouriteStockFragmentTest {

    /**
     * Test Empty state of Favorite fragment
     */
    @Test
    fun test_empty_state() {
        launchFragmentInContainer<FavouriteStocksFragment>()

        onView(withId(R.id.empty_state_image_view)).check(matches(isDisplayed()))
        onView(withId(R.id.empty_state_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.favouriteStocksList)).check(matches(not((isDisplayed()))))
    }
}