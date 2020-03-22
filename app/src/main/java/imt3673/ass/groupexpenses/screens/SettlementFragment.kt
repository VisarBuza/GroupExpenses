package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import imt3673.ass.groupexpenses.MainActivity

import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.adapters.SettlementAdapter
import imt3673.ass.groupexpenses.databinding.FragmentSettlementBinding

/**
 * A simple [Fragment] subclass.
 */
class SettlementFragment : Fragment() {

    private lateinit var binding: FragmentSettlementBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settlement, container, false)

        val adapter = SettlementAdapter()
        binding.settlementList.adapter = adapter

        if ((activity as MainActivity).expenses.allExpenses().isNotEmpty()) {
            (activity as MainActivity).updateSettlement()
            adapter.data = (activity as MainActivity).settlement
        }

        return binding.root
    }

}
