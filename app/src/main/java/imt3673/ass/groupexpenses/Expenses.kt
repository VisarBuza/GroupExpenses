package imt3673.ass.groupexpenses

import java.lang.Exception
import kotlin.system.exitProcess

/**
 * Represents all the expenses of the group of people.
 *
 * TODO implement the functionality of this class
 */
class Expenses(private val expenseList: MutableList<SingleExpense> = mutableListOf()) {

    // NOTE: Expenses MUST have a default, non-argument constructor.
    // Adds new expense to the expenses list.
    // If the Person does not exist in the expenses,
    //   the person is added to the list, and false is returned.
    // If the Person already exist in the expenses,
    // the new expense amount is added to the person's existing amount and true is returned.
    // There should only be
    // one instance of SingleExpense associated with a single person in the expenses.
    // No duplicates.
    // TODO implement the method
    fun add(expense: SingleExpense): Boolean {

        val duplicate = this.expenseList.firstOrNull { it.person == expense.person }
        var number = 2
        if (duplicate == null) {
            this.expenseList.add(expense)
            return false
        }

        this.expenseList.remove(duplicate)
        this.expenseList.add(SingleExpense(expense.person, expense.amount + duplicate.amount))

        return true
    }

    // Replaces the expense for a given person
    // This method is similar to #addExpense, however, instead of adding
    // the claim amount to the existing person, it replaces it instead.
    // TODO implement the method
    fun replace(expense: SingleExpense): Boolean {
        if (!this.expenseList.any { it.person == expense.person }) {
            this.expenseList.add(expense)
            return false
        }

        this.expenseList.remove(this.expenseList.find { it.person == expense.person })
        this.expenseList.add(expense)

        return true
    }

    // Removes an expense association for this person.
    // If the person exists in the expenses, it returns true.
    // Otherwise, it returns false.
    // TODO implement the method
    fun remove(person: String): Boolean {

        val expense = this.expenseList.firstOrNull { it.person == person }

        if (expense != null) {
            this.expenseList.remove(expense)
            return true
        }

        return false
    }

    // Returns the amount of expenses for a given person.
    // If the person does not exist, the function returns failed result.
    // TODO implement the method
    fun amountFor(person: String): Result<Long> {
        val expense = this.expenseList.firstOrNull { it.person == person }

        if (expense != null) {
            return Result.success(expense.amount)
        }

        return Result.failure(Exception("Failed Result"))
    }

    // Returns the list of all expenses.
    // If no expenses have been added yet, the function returns an empty list.
    // TODO implement the method
    fun allExpenses(): List<SingleExpense> {
        return this.expenseList
    }

    // Makes a deep copy of this expense instance
    fun copy(): Expenses {
        val exp = Expenses()
        allExpenses().forEach {
            exp.add(SingleExpense(it.person, it.amount, it.description))
        }
        return exp
    }
}