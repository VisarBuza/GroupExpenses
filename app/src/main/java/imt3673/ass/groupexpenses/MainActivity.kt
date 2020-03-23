package imt3673.ass.groupexpenses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import imt3673.ass.groupexpenses.Utils.Expenses
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.calculateSettlement



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}
