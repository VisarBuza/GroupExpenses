package imt3673.ass.groupexpenses

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.DecimalFormatSymbols

/**
 * Tests for utility functions in Constants.kt
 *
 */
class ConstantsTest {

    @Test
    fun convertAmountToString_420() {
        val separatorChar: Char = DecimalFormatSymbols.getInstance().decimalSeparator
        assertEquals("4" + separatorChar + "20", convertAmountToString(420))
    }

    @Test
    fun convertAmountToString_various() {
        val separatorChar: Char = DecimalFormatSymbols.getInstance().decimalSeparator
        val data = mapOf(
            1234L to Pair("12", "34"),
            -1234L to Pair("-12", "34"),
            0L to Pair("0", "00"),
            1L to Pair("0", "01"),
            1001L to Pair("10", "01"),
            (-1L) to Pair("-0", "01"))
        data.forEach {
            assertEquals(it.value.first + separatorChar + it.value.second,
                convertAmountToString(it.key))
        }
    }

    @Test
    fun convertStringToAmount_1999() {
        val res1 = convertStringToAmount("19.99")
        assertEquals(1999L, res1.getOrNull())

        val res2 = convertStringToAmount("19,99")
        assertTrue(res2.isSuccess)
        assertEquals(1999L, res2.getOrNull())
    }

    @Test
    fun convertStringToAmount_variousCorrect() {
        val data = mapOf(
            "-1.23" to -123L,
            "1.23" to 123L,
            "0.01" to 1L,
            "0" to 0L,
            "-0.01" to -1L,
            "180" to 18000L,
            "1,23" to 123L,
            "0,01" to 1L)

        data.forEach {
            val res = convertStringToAmount(it.key)
            assertTrue(res.isSuccess)
            assertEquals(it.value, res.getOrNull())
        }
    }


}
