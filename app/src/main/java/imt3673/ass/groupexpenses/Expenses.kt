package imt3673.ass.groupexpenses

/**
 * Represents all the expenses of the group of people.
 *
 * TODO implement the functionality of this class
 */
class Expenses {

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
        return true
    }

    // Replaces the expense for a given person
    // This method is similar to #addExpense, however, instead of adding
    // the claim amount to the existing person, it replaces it instead.
    // TODO implement the method
    fun replace(expense: SingleExpense): Boolean {

        return true
    }

    // Removes an expense association for this person.
    // If the person exists in the expenses, it returns true.
    // Otherwise, it returns false.
    // TODO implement the method
    fun remove(person: String): Boolean {
        return false
    }

    // Returns the amount of expenses for a given person.
    // If the person does not exist, the function returns failed result.
    // TODO implement the method
    fun amountFor(person: String): Result<Long> {
        return Result.success(0)
    }

    // Returns the list of all expenses.
    // If no expenses have been added yet, the function returns an empty list.
    // TODO implement the method
    fun allExpenses(): List<SingleExpense> {
        return listOf()
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