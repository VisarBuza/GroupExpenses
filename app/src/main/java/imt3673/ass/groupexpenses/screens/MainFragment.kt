package imt3673.ass.groupexpenses.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.addButton.setOnClickListener {view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_addExpenseFragment)
        }

        binding.settlementButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainFragment_to_settlementFragment)
        }

        return binding.root
    }

}
