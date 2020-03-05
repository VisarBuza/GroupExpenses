package imt3673.ass.groupexpenses

import androidx.core.text.isDigitsOnly
import java.util.*
import java.lang.Double.parseDouble
import java.lang.NumberFormatException
import kotlin.math.abs
import kotlin.math.sin

/**
 * Keep all the package level functions and constants here.
 * Keep public classes in their respective files, one per file, with consistent
 * naming conventions.
 */


/**
 * Sanitize the name text entries following the specification.
 * See wiki and tests for details.
 */
fun sanitizeName(name: String): String {
    // TODO implement the logic

    return name
        .filter { it.isLetter() || it == '-' || it.isWhitespace() }
        .trim()
        .split("\\s+".toRegex())
        .map { it.toLowerCase(Locale.ROOT).capitalize() }
        .joinToString(separator = " ", limit = 2, truncated = "")
        .split("-")
        .map { it.capitalize() }
        .joinToString("-")
        .trimEnd()
}

/**
 * Utility method for settlement calculations.
 * Takes the Expenses instance, and produces a list of Transactions.
 */
fun calculateSettlement(expenses: Expenses): List<Transaction> {
    // TODO implement the logic

    // dummy implementation for a simple single case
    // Alice -> 20
    // Bob -> 20
    // Charlie -> 30
    // David -> 50

    // Only one resonable solution:
    // Alice to David -> 10
    // Bob to David -> 10
    if (expenses.allExpenses().size <= 1) {
        return listOf()
    }

    val transactions: MutableList<Transaction> = mutableListOf()

    var totalAmount: Long = 0
    expenses.allExpenses().forEach { expense ->
        totalAmount += expense.amount
    }

    val everyoneShouldPay = (totalAmount / expenses.allExpenses().size)

    val temp = mutableListOf<Pair<String, Long>>()

    expenses.allExpenses().forEach { expense ->
        temp.add(Pair(expense.person, everyoneShouldPay - expense.amount))
    }

    temp.sortBy { it.second }

    while (temp.size != 0) {
        val person1 = temp.first()
        val person2 = temp.last()

        if((person2.second + person1.second) in -10..10) {
            temp.remove(person2)
            temp.remove(person1)
            transactions.add(Transaction(person2.first, person1.first, person2.second))
        } else if (person1.second + person2.second > 0) {
            temp.remove(person1)
            temp.remove(person2)
            temp.add(Pair(person2.first, person1.second + person2.second))
            transactions.add(Transaction(person2.first, person1.first, abs(person1.second)))
        } else {
            temp.remove(person1)
            temp.add(0, Pair(person1.first, person1.second + person2.second))
            temp.remove(person2)
            transactions.add(Transaction(person2.first, person1.first, abs(person2.second)))
        }
        temp.sortBy { it.second }
    }

    return transactions
}


/**
 * Converts a given Long amount into a formatted String, with
 * two decimal places. Note, the decimal place separator can be
 * dot or comma, subject to the current locale used.
 */
fun convertAmountToString(amount: Long): String {

    // TODO implement the conversion from Amount
    // that is of type Long to String
    // The string should be formatted with 2 decimal places, with the locale-defined
    // decimal point separator.

    // Examples, with dot as decimal separator:
    // 20 -> "0.20"
    // 500 -> "5.00"
    // 1234 -> "12.34"
    return ("%.2f".format(amount / 100.0))
}

/**
 * Convert from String to Amount. If error, return failed result with
 * appropriate error string.
 */
fun convertStringToAmount(value: String): Result<Long> {

    // TODO implement the conversion from String to Amount

    if (value.any { it.isLetter() }) {
        return Result.failure(Throwable("Not a number"))
    } else {
        return if (value.any { it == ','|| it == '.' })
            Result.success(value.filter { it.isDigit() || it == '-' }.toLong())
        else
            Result.success(value.toLong() * 100)
    }
}