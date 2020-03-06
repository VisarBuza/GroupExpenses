package imt3673.ass.groupexpenses.Utils

import java.util.*
import kotlin.math.abs

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
    val totalAmount: Long = expenses.allExpenses().map { it.amount }.sum()
    val averageAmountPerPerson = totalAmount / expenses.allExpenses().size

    val balance = mutableListOf<Pair<String, Long>>()
    expenses.allExpenses().forEach { balance.add(Pair(it.person, averageAmountPerPerson - it.amount)) }
    balance.sortBy { it.second }

    while (balance.size != 0) {
        val person1 = balance.first()
        val person2 = balance.last()

        when (person1.second + person2.second) {
            in -expenses.allExpenses().size/2 * 10..expenses.allExpenses().size/2 * 10-> {
                balance.remove(person2)
                balance.remove(person1)
            }
            in 1..Long.MAX_VALUE -> {
                balance.remove(person1)
                balance.remove(person2)
                balance.add(Pair(person2.first, person1.second + person2.second))
            }
            else -> {
                balance.remove(person1)
                balance.add(0, Pair(person1.first, person1.second + person2.second))
                balance.remove(person2)
            }
        }
        transactions.add(
            Transaction(
                person2.first,
                person1.first,
                if (abs(person1.second) > abs(person2.second)) abs(person2.second) else abs(person1.second)
            )
        )
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
    }

    return Result.success((value.replace(',', '.').toFloat() * 100).toLong())
}