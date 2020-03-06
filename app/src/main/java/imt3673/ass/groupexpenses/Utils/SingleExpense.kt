package imt3673.ass.groupexpenses.Utils

/**
 * Represents a single expense.
 */
class SingleExpense(val person: String, val amount: Long, var description: String = "") {

    override fun toString(): String {
        return "Expense{$person, $amount}"
    }
}
