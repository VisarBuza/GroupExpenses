package imt3673.ass.groupexpenses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import imt3673.ass.groupexpenses.Utils.Expenses
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.calculateSettlement
import kotlin.math.exp

class ExpenseViewModel: ViewModel() {

    val expenses: Expenses = Expenses()

    private var _settlement = MutableLiveData<List<Transaction>>()
    val settlement: LiveData<List<Transaction>>
        get() = _settlement


    fun updateSettlement(){
        _settlement.value = calculateSettlement(expenses)
    }
}