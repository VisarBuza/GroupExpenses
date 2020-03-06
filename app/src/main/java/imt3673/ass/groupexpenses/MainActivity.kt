package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import imt3673.ass.groupexpenses.Utils.Expenses
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.calculateSettlement

class MainActivity : AppCompatActivity() {

    // The storage for all expenses
    val expenses: Expenses = Expenses()
    var settlement = listOf<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expenses.add(SingleExpense("Visar", 3000, "Food"))
        expenses.add(SingleExpense("Ardit", 5000, "Accomodation"))
        setupUI()
    }

    // implements the settlement calculation and keeps it in this.settlement
    fun updateSettlement() {
        this.settlement = calculateSettlement(this.expenses)
    }

    // TODO implement setupUI method
    private fun setupUI() {

    }

}
