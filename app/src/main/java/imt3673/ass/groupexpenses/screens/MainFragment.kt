package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import imt3673.ass.groupexpenses.MainActivity
import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.Expenses
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        var totalExpenses: Long = 0
        var averageExpense: Long = 0
        if (!(activity as MainActivity).expenses.allExpenses().isEmpty()) {
            totalExpenses = (activity as MainActivity).expenses.allExpenses().map { it.amount }.sum()
            averageExpense = totalExpenses / (activity as MainActivity).expenses.allExpenses().size
            (activity as MainActivity).expenses.allExpenses().forEach { renderTable(it) }
        }

        binding.txtExpensesTotal.text = totalExpenses.toString()
        binding.txtExpensesAvr.text = averageExpense.toString()


        binding.btnAddData.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_addExpenseFragment)
        }

        binding.btnSettlement.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_settlementFragment)
        }

        return binding.root
    }

    private fun renderTable(expense: SingleExpense) {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        row.setPadding(10, 10, 10, 10)

        val person = TextView(context)
        person.text = expense.person

        val amount = TextView(context)
        amount.text = expense.amount.toString()

        val description = TextView(context)
        description.text = expense.description

        row.addView(person)
        row.addView(amount)
        row.addView(description)

        binding.tblExpenses.addView(row)
    }

}
