package imt3673.ass.groupexpenses

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DecimalFormatSymbols

/**
 * Instrumented tests for the MainActivity and some click-through the app.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val intentsRule = IntentsTestRule(MainActivity::class.java)

    /**
     * Checks if pressing the Add button switches to DataEntry.
     */
    @Test
    fun clickBtnAddData_viewChange() {
        // Type text and then press the button.
        onView(withId(R.id.btn_add_data))
            .perform(click())
        // Not sure if this is needed
        Thread.sleep(1000)

        onView(withId(R.id.dataentry_view))
            .check(matches(isDisplayed()))
    }

    /**
     * Checks if the initial data is set to 0.
     */
    @Test
    fun initialData_check() {
        val dot: Char = DecimalFormatSymbols.getInstance().decimalSeparator
        // Check if the total and avr are set initially to 0
        onView(allOf(
            withId(R.id.txt_expenses_total),
            withText("0${dot}00")))
            .check(matches(isDisplayed()))

        onView(allOf(
            withId(R.id.txt_expenses_avr),
            withText("0${dot}00")))
            .check(matches(isDisplayed()))

        // Settlement button should be disabled
        onView(withId(R.id.btn_settlement))
            .check(matches(isDisplayed()))
            .check(matches(not(isEnabled())))

    }

    /**
     * Checks if pressing the AddData functionality works.
     */



}
