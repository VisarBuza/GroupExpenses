package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.databinding.FragmentSettlementBinding

/**
 * A simple [Fragment] subclass.
 */
class SettlementFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentSettlementBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settlement, container, false)

        return binding.root
    }

}
