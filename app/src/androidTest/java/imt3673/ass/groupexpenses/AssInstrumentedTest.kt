package imt3673.ass.groupexpenses

import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented tests.  This is a test template.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class AssInstrumentedTest {

    @get:Rule
    val intentsRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("imt3673.ass.groupexpenses", appContext.packageName)
    }

}
