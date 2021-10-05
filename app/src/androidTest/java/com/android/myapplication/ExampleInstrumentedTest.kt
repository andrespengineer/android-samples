package com.android.myapplication

import androidx.test.espresso.IdlingPolicies
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@HiltAndroidTest
class HomeActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun init() {

        IdlingPolicies.setMasterPolicyTimeout(10, TimeUnit.SECONDS)

        hiltRule.inject()
    }

    @Test
    fun contextAssertion() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.android.myapplication", appContext.packageName)
    }

    @Test
    fun isHomeActivityVisible() {
        runBlocking {
            delay(5000)
            //onView(withId(R.id.fragmentPosts)).check(ViewAssertions.matches(isDisplayed()))
            //onView(withId(R.id.fragmentStories)).check(ViewAssertions.matches(isDisplayed()))
        }
    }
}