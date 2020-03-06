package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAddExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense, container, false)

        binding.btnCancel.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addExpenseFragment_to_mainFragment)
        }

        binding.btnAddExpense.setOnClickListener { view: View ->
            (activity as MainActivity).expenses.add(SingleExpense(
                sanitizeName(binding.editPerson.text.toString()),
                convertStringToAmount(binding.editAmount.text.toString()).getOrThrow(),
                binding.editDescription.text.toString()
            ))
            view.findNavController().navigate(R.id.action_addExpenseFragment_to_mainFragment)
        }

        return binding.root
    }

}
