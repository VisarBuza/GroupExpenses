package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import imt3673.ass.groupexpenses.ExpenseViewModel
import imt3673.ass.groupexpenses.MainActivity
import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.convertAmountToString
import imt3673.ass.groupexpenses.adapters.ExpenseAdapter
import imt3673.ass.groupexpenses.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.btnSettlement.isEnabled = false

        expenseViewModel = activity?.let { ViewModelProviders.of(it).get(ExpenseViewModel::class.java) }!!

        var totalExpenses: Long = 0
        var averageExpense: Long = 0

        val adapter = ExpenseAdapter()
        binding.expenseList.adapter = adapter

        if (expenseViewModel.expenses.allExpenses().isNotEmpty()) {
            totalExpenses = expenseViewModel.expenses.allExpenses().map { it.amount }.sum()
            averageExpense = totalExpenses / expenseViewModel.expenses.allExpenses().size
            adapter.data = expenseViewModel.expenses.allExpenses()
            binding.btnSettlement.isEnabled = true
        }

        binding.txtExpensesTotal.text = convertAmountToString(totalExpenses)
        binding.txtExpensesAvr.text = convertAmountToString(averageExpense)

        binding.btnAddData.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_addExpenseFragment)
        }

        binding.btnSettlement.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_settlementFragment)
        }

        return binding.root
    }
}
