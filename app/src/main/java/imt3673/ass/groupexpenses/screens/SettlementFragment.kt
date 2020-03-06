package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import imt3673.ass.groupexpenses.MainActivity

import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.calculateSettlement
import imt3673.ass.groupexpenses.Utils.convertAmountToString
import imt3673.ass.groupexpenses.databinding.FragmentSettlementBinding

/**
 * A simple [Fragment] subclass.
 */
class SettlementFragment : Fragment() {

    private lateinit var binding: FragmentSettlementBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settlement, container, false)

        if ((activity as MainActivity).expenses.allExpenses().isNotEmpty()) {
            (activity as MainActivity).updateSettlement()
            (activity as MainActivity).settlement.forEach { renderTable(it) }
        }

        return binding.root
    }

    private fun renderTable(transaction: Transaction) {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        row.setPadding(10, 10, 10, 10)

        val payer = TextView(context)
        payer.text = transaction.payer
        payer.gravity = Gravity.CENTER

        val payee = TextView(context)
        payee.text = transaction.payee
        payee.gravity = Gravity.CENTER

        val amount = TextView(context)
        amount.text = convertAmountToString(transaction.amount)
        amount.gravity = Gravity.CENTER

        row.addView(payer)
        row.addView(payee)
        row.addView(amount)

        binding.tblSettlements.addView(row)
    }

}
