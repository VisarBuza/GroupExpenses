package imt3673.ass.groupexpenses

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DecimalFormatSymbols

/**
 * Instrumented tests for the DataEntry and some click-through the UI.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class DataEntryViewTest {

    @get:Rule
    val intentsRule = IntentsTestRule(MainActivity::class.java)

    // Utility function to move the UI to DataEntryView
    private fun switchToDataEntry() {
        // Type text and then press the button.
        onView(withId(R.id.btn_add_data))
            .perform(click())
        // Not sure if this is needed
        Thread.sleep(1000)
        // check that we are in DataEntry view
        onView(withId(R.id.dataentry_view))
            .check(matches(isDisplayed()))
    }

    /**
     * Checks if name sanitation works.
     */
    @Test
    fun edit_Person_withWrongCharacters() {
        switchToDataEntry()
        val wrongData = listOf(
            "34Bob", "Bob34", "#Bob", "Bo\$b", "B0B",
            "Bob 34", "Bob #", "Bob B#b")
        // lets add legit description and amount
        onView(withId(R.id.edit_amount)).perform(replaceText("10"))
        onView(withId(R.id.edit_description)).perform(replaceText("Taxi"))

        wrongData.forEach {
            onView(withId(R.id.edit_person))
                .perform(replaceText(it))
            // button is not enabled
            onView(withId(R.id.btn_add_expense))
                .check(matches(not(isEnabled())))

            // there is an error message
            onView(withId(R.id.txt_add_expenses_error))
                .check(matches(withText(containsString(" "))))
        }

        // Not sure if this is needed
        Thread.sleep(1000)

        onView(withId(R.id.dataentry_view))
            .check(matches(isDisplayed()))
    }


}
