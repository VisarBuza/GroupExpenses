package imt3673.ass.groupexpenses

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import imt3673.ass.groupexpenses.Utils.Expenses
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.calculateSettlement

const val KEY_PERSONS = "persons_key"
const val KEY_AMOUNTS = "amounts_key"
const val KEY_DESCRIPTIONS= "descriptions_key"

class MainActivity : AppCompatActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null) {
            val persons = savedInstanceState.getStringArray(KEY_PERSONS)
            val amounts = savedInstanceState.getLongArray(KEY_AMOUNTS)
            val descriptions = savedInstanceState.getStringArray(KEY_DESCRIPTIONS)

            persons!!.forEachIndexed { index, s ->
                expenses.add(SingleExpense(s, amounts!![index], descriptions!![index]))
            }
        }
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray(KEY_PERSONS, expenses.allExpenses().map { it.person }.toTypedArray())
        outState.putLongArray(KEY_AMOUNTS, expenses.allExpenses().map { it.amount }.toLongArray())
        outState.putStringArray(KEY_DESCRIPTIONS, expenses.allExpenses().map { it.description }.toTypedArray())
    }

    // implements the settlement calculation and keeps it in this.settlement
    fun updateSettlement() {
        this.settlement = calculateSettlement(this.expenses)
    }
}
