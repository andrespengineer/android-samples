package com.android.myapplication

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import com.android.myapplication.ui.home.activities.ActivityHome
import org.junit.Before
import org.junit.Test
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After


@HiltAndroidTest
class PostsFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val homeActivityRule = ActivityScenarioRule(ActivityHome::class.java)

    @Before
    fun start(){
        hiltRule.inject()
    }

    @Test
    fun likePost(){
        runBlocking {
            delay(4000)
            onView(withId(R.id.fragmentPosts)).perform(doubleClick())
            onView(withId(R.id.lavLikeAnimation)).check(ViewAssertions.matches(withEffectiveVisibility(Visibility.VISIBLE)))
        }
    }

    @After
    fun tearDown(){

    }
}