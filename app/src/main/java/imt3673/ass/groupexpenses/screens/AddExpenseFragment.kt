package imt3673.ass.groupexpenses.screens

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import imt3673.ass.groupexpenses.ExpenseViewModel
import imt3673.ass.groupexpenses.MainActivity

import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.convertStringToAmount
import imt3673.ass.groupexpenses.Utils.sanitizeName
import imt3673.ass.groupexpenses.databinding.FragmentAddExpenseBinding

/**
 * A simple [Fragment] subclass.
 */
class AddExpenseFragment : Fragment() {

    lateinit var binding: FragmentAddExpenseBinding
    lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)

        binding.btnCancel.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addExpenseFragment_to_mainFragment)
        }

        expenseViewModel = activity?.let { ViewModelProviders.of(it).get(ExpenseViewModel::class.java) }!!


        binding.btnAddExpense.setOnClickListener { view: View ->
            expenseViewModel.expenses.add(
                SingleExpense(
                    sanitizeName(binding.editPerson.text.toString()),
                    convertStringToAmount(binding.editAmount.text.toString()).getOrThrow(),
                    binding.editDescription.text.toString()
                )
            )
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.btnAddExpense.windowToken, 0)
            view.findNavController().navigate(R.id.action_addExpenseFragment_to_mainFragment)
        }

        binding.btnAddExpense.isEnabled = false

        binding.editPerson.addTextChangedListener(btnAddTextWatcher)
        binding.editAmount.addTextChangedListener(btnAddTextWatcher)
        binding.editDescription.addTextChangedListener(btnAddTextWatcher)

        return binding.root
    }

    private val btnAddTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val name = binding.editPerson.text.toString()
            val amount = binding.editAmount.text.toString()
            val description = binding.editDescription.text.toString()
            if (!name.contains("[^\\s-a-zA-Z]".toRegex()) && amount.isNotBlank() && description.isNotBlank()) {
                binding.btnAddExpense.isEnabled = true
                binding.btnAddExpense.isClickable = true
                binding.txtAddExpensesError.text = ""
                binding.txtAddExpensesError.visibility = View.GONE
            } else {
                binding.btnAddExpense.isEnabled = false
                binding.btnAddExpense.isClickable = false
                binding.txtAddExpensesError.visibility = View.VISIBLE
                if (name.isEmpty()) {
                    binding.txtAddExpensesError.visibility = View.GONE
                } else if (name.contains("[^\\s-a-zA-Z]".toRegex())) {
                    binding.txtAddExpensesError.text = "Invalid Name"
                } else {
                    binding.txtAddExpensesError.text = "Invalid"
                }
            }
        }
    }
}