package imt3673.ass.groupexpenses

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception
import java.text.DecimalFormatSymbols
import kotlin.math.abs
import kotlin.random.Random

/**
 * Tests for utility functions in Constants.kt
 */
class ConstantsTest {

    //
    // simple utility functions for helping with Settlement testing
    //
    private fun calculateTotalAndAvr(exp: Expenses): Pair<Long,Long> {
        // lets handle first the two edge cases
        if (exp.allExpenses().isEmpty()) return Pair(0L, 0L)
        if (exp.allExpenses().size == 1) return Pair(
            exp.allExpenses()[0].amount,
            exp.allExpenses()[0].amount
        )
        var sum = 0L
        exp.allExpenses().forEach {
            sum += it.amount
        }
        return Pair(sum, sum / exp.allExpenses().size)
    }

    private fun executeTransaction(exp: Expenses, tx: Transaction): Boolean {
        val payerR = exp.amountFor(tx.payer)
        val payeeR = exp.amountFor(tx.payee)
        if (payerR.isFailure || payeeR.isFailure) return false
        assertTrue(exp.replace(SingleExpense(tx.payer, payerR.getOrDefault(0)+tx.amount, "settled")))
        assertTrue(exp.replace(SingleExpense(tx.payee, payeeR.getOrDefault(0)-tx.amount, "settled")))
        return true
    }

    private fun testSettlement(expIn: Expenses) {
        val pplCount = expIn.allExpenses().size
        val origTotalAndAvr = calculateTotalAndAvr(expIn)
        val resTransactions = calculateSettlement(expIn)

        val expOut = expIn.copy()
        resTransactions.forEach {
            val r = executeTransaction(expOut, it)
            assertTrue(r)
            if (!r) throw Exception()
        }

        val settledTotalAndAvr = calculateTotalAndAvr(expOut)

        assertEquals(origTotalAndAvr.first, settledTotalAndAvr.first)
        assertEquals(origTotalAndAvr.second, settledTotalAndAvr.second)

        var largestDiff = 0L
        expOut.allExpenses().forEach {
            val diff = origTotalAndAvr.second - it.amount
            if (diff > largestDiff) largestDiff = diff
            assertTrue((diff) < (pplCount - 1))
        }

        if (largestDiff > 1) {
            println("WARNING: largest difference after settlement $largestDiff")
        }
    }

    /**
     * Default test case for dummy data to test the tests.
     * It should pass with the dummy implementation in the template.
     */
    @Test
    fun calculateSettlement_default() {
        // dummy implementation for a simple single case
        // Alice -> 20
        // Bob -> 20
        // Charlie -> 30
        // David -> 50
        val exp = Expenses()
        exp.add(SingleExpense("Alice", 2000, "bus"))
        exp.add(SingleExpense("Bob", 2000, "bus"))
        exp.add(SingleExpense("Charlie", 3000, "ice cream"))
        exp.add(SingleExpense("David", 5000, "train"))

        testSettlement(exp)
    }

    // This test demonstrates rounding error when calculating/executing
    // the settlement operation. See the printout.
    @Test
    fun calculateSettlement_6() {
        // dummy implementation for a simple single case
        // Alice -> 20
        // Bob -> 20
        // Charlie -> 30
        // David -> 50
        val exp = Expenses()
        exp.add(SingleExpense("Alice", 0, "bus"))
        exp.add(SingleExpense("Bob", 0, "bus"))
        exp.add(SingleExpense("Charlie", 0, "ice cream"))
        exp.add(SingleExpense("David", 6, "train"))

        testSettlement(exp)
    }

    @Test
    fun calculateSettlement_0_or_1person() {
        val data0 = Expenses()
        val tx0 = calculateSettlement(data0)
        assertEquals(0, tx0.size)

        val data1 = Expenses()
        data1.add(SingleExpense("A", 10, "a"))
        val tx1 = calculateSettlement(data1)
        assertEquals(0, tx1.size)
    }

    @Test
    fun calculateSettlement_2people() {
        (1..10).forEach { _ ->
            val exp = Expenses()
            (1..2).forEach { _ ->
                val a = abs(Random.nextInt()).toLong()
                val name = "a" + abs(Random.nextInt()).toString() + "_" + a.toString()
                exp.add(SingleExpense(name, a, "a"))
            }
            testSettlement(exp)
        }
    }

    @Test
    fun calculateSettlement_3people() {
        (1..10).forEach { _ ->
            val exp = Expenses()
            // Let's be proper, and make sure that we have unique names
            val namesSet = mutableSetOf<String>()
            (1..3).forEach { _ ->
                val a = abs(Random.nextInt()).toLong()
                var name = "a" + abs(Random.nextInt()).toString() + "_" + a.toString()
                while (namesSet.contains(name)) {
                    name = "a" + abs(Random.nextInt()).toString() + "_" + a.toString()
                }
                namesSet.add(name)
                exp.add(SingleExpense(name, a, "a"))
            }
            testSettlement(exp)
        }
    }

    @Test
    fun calculateSettlement_6people() {
        (1..10).forEach { _ ->
            val exp = Expenses()
            val namesSet = mutableSetOf<String>()
            (1..6).forEach { _ ->
                val a = abs(Random.nextInt()).toLong()
                var name = "a" + abs(Random.nextInt()).toString() + "_" + a.toString()
                while (namesSet.contains(name)) {
                    name = "a" + abs(Random.nextInt()).toString() + "_" + a.toString()
                }
                namesSet.add(name)
                exp.add(SingleExpense(name, a, "a"))
            }
            testSettlement(exp)
        }
    }





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
            10L to Pair("0", "10"),
            100L to Pair("1","00"),
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
