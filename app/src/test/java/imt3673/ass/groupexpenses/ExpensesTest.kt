package imt3673.ass.groupexpenses

import org.junit.Assert
import org.junit.Test

class ExpensesTest {

    @Test
    fun add_new() {
        val alice = SingleExpense("Alice", 100)
        val bob = SingleExpense("Bob", 200)
        val exp = Expenses()
        Assert.assertEquals(0, exp.allExpenses().size)

        // Adding Alice
        val r = exp.add(alice)
        Assert.assertFalse(r)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice.amount, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice.person, exp.allExpenses()[0].person)
        // Adding Bob
        exp.add(bob)
        Assert.assertEquals(2, exp.allExpenses().size)
    }

    @Test
    fun add_AliceTwice() {
        val alice = SingleExpense("Alice", 100)
        val exp = Expenses()
        Assert.assertEquals(0, exp.allExpenses().size)

        // Adding Alice
        val r = exp.add(alice)
        Assert.assertFalse(r)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice.amount, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice.person, exp.allExpenses()[0].person)
        // Adding Alice again
        val r2 = exp.add(alice)
        Assert.assertTrue(r2)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice.amount * 2, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice.person, exp.allExpenses()[0].person)
    }

    @Test
    fun replace_Alice200() {
        val alice = SingleExpense("Alice", 100)
        val alice200 = SingleExpense("Alice", 200)
        val exp = Expenses()
        Assert.assertEquals(0, exp.allExpenses().size)

        // Adding Alice
        val r1 = exp.add(alice)
        Assert.assertFalse(r1)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice.amount, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice.person, exp.allExpenses()[0].person)

        // Replace
        val r = exp.replace(alice200)
        Assert.assertTrue(r)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice200.amount, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice200.person, exp.allExpenses()[0].person)
    }

    @Test
    fun replace_new() {
        val alice = SingleExpense("Alice", 100)
        val exp = Expenses()
        Assert.assertEquals(0, exp.allExpenses().size)

        // Adding Alice
        val r = exp.replace(alice)
        Assert.assertFalse(r)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice.amount, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice.person, exp.allExpenses()[0].person)
    }

    @Test
    fun remove_empty_non_existing() {
        val exp = Expenses()
        Assert.assertFalse(exp.remove("Alice"))
    }

    @Test
    fun remove_Alice() {
        val alice = SingleExpense("Alice", 100)
        val exp = Expenses()
        Assert.assertEquals(0, exp.allExpenses().size)
        Assert.assertFalse(exp.add(alice))
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertTrue(exp.remove(alice.person))
        Assert.assertEquals(0, exp.allExpenses().size)
    }

    @Test
    fun remove_Alice_twice() {
        val alice = SingleExpense("Alice", 100)
        val exp = Expenses()
        Assert.assertEquals(0, exp.allExpenses().size)

        // Adding Alice
        val r = exp.replace(alice)
        Assert.assertFalse(r)
        Assert.assertEquals(1, exp.allExpenses().size)
        Assert.assertEquals(alice.amount, exp.allExpenses()[0].amount)
        Assert.assertEquals(alice.person, exp.allExpenses()[0].person)

        //Removing Alice
        val r2 = exp.remove(alice.person)
        Assert.assertTrue(r2)
        Assert.assertEquals(0, exp.allExpenses().size)

        //Removing Alice when she's not in the list
        val r3 = exp.remove(alice.person)
        Assert.assertFalse(r3)
        Assert.assertEquals(0, exp.allExpenses().size)
    }

}