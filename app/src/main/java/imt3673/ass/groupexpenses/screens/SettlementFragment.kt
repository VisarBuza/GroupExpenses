package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import imt3673.ass.groupexpenses.ExpenseViewModel
import imt3673.ass.groupexpenses.MainActivity

import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.adapters.SettlementAdapter
import imt3673.ass.groupexpenses.databinding.FragmentSettlementBinding

/**
 * A simple [Fragment] subclass.
 */
class SettlementFragment : Fragment() {

    private lateinit var binding: FragmentSettlementBinding
    private lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settlement, container, false)

        expenseViewModel = activity?.let { ViewModelProviders.of(it).get(ExpenseViewModel::class.java) }!!

        val adapter = SettlementAdapter()
        binding.settlementList.adapter = adapter

        expenseViewModel.updateSettlement()
        adapter.data = expenseViewModel.settlement.value!!

        return binding.root
    }

}
