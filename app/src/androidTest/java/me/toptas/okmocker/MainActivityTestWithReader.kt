package me.toptas.okmocker

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import me.toptas.okmockerreader.OkMockerIdlingResource
import me.toptas.okmockerreader.OkMockerReadInterceptor
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTestWithReader {

    @get:Rule
    val rule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(OkMockerIdlingResource.instance)
        NetworkManager.instance.clientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(OkMockerReadInterceptor(MyReader()))
        }

        val intent = Intent()
        rule.launchActivity(intent)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(OkMockerIdlingResource.instance)
    }

    @Test
    fun test() {
        Espresso.onView(ViewMatchers.withId(R.id.btn))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.tvText))
            .check(ViewAssertions.matches(ViewMatchers.withText("result_from_reader")))

    }
}