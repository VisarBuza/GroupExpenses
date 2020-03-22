package imt3673.ass.groupexpenses.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.Transaction
import imt3673.ass.groupexpenses.Utils.convertAmountToString

class SettlementAdapter: RecyclerView.Adapter<SettlementAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        private val settlement: TextView = itemView.findViewById(R.id.lbl_settlement)
        private val amount: TextView = itemView.findViewById(R.id.lbl_entry_amount)

        fun bind(item: Transaction) {
            val settlementText = "${item.payer} owes ${item.payee}"
            settlement.text = settlementText
            amount.text = convertAmountToString(item.amount)
        }

        companion object {
            fun from (parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.entry_settlement, parent, false)
                return ViewHolder(view)
            }
        }
    }

    var data = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}