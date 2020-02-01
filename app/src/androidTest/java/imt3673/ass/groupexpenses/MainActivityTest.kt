package imt3673.ass.groupexpenses

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Assert
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
    @Test
    fun addSingleRow() {
        val data = SingleExpense("Alice", 1000, "Buss")

        // We should have the new row in the Expenses
        Assert.assertEquals(0, intentsRule.activity.expenses.allExpenses().size)

        // MainActivity view
        onView(withId(R.id.tbl_expenses))
            .check(matches(isDisplayed()))

        // Type text and then press the button.
        onView(withId(R.id.btn_add_data))
            .perform(click())
        // Not sure if this is needed
        Thread.sleep(1000)

        // We should be in DataEntry view
        onView(withId(R.id.dataentry_view))
            .check(matches(isDisplayed()))

        // Add Expense button should not be enabled yet
        onView(withId(R.id.btn_add_expense))
            .check(matches(not(isEnabled())))

        // type name
        onView(withId(R.id.edit_person))
            .perform(replaceText(data.person))

        // type info for the expense
        onView(withId(R.id.edit_description))
            .perform(replaceText(data.description))

        // Add Expense button should not be enabled yet
        onView(withId(R.id.btn_add_expense))
            .check(matches(not(isEnabled())))

        // type amount
        onView(withId(R.id.edit_amount))
            .perform(replaceText(convertAmountToString(data.amount)))

        // Add Expense button should now be enabled
        onView(withId(R.id.btn_add_expense))
            .check(matches(isEnabled()))
            .perform(click())

        // We should have the new row in the Expenses now
        val exp = intentsRule.activity.expenses.allExpenses()
        Assert.assertEquals(1, exp.size)
        Assert.assertEquals(data.person, exp[0].person)
        Assert.assertEquals(data.amount, exp[0].amount)
        Assert.assertEquals(data.description, exp[0].description)

        // and the MainActivity should be back
        onView(withId(R.id.tbl_expenses)).check(matches(isDisplayed()))

        // we should have Alice in the table
        onView(withId(R.id.tbl_expenses)).check(matches(withText(data.person)))
    }


}
