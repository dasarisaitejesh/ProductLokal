package com.example.productlokal

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.productlokal", appContext.packageName)
    }

    fun testUiComponentsDisplayed() {
        // Launch the MainActivity
        ActivityScenario.launch(MainActivity::class.java)

        // Check if the UI components are displayed
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.swipeRefresh)).check(matches(isDisplayed()))
    }


}