package imt3673.ass.groupexpenses.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import imt3673.ass.groupexpenses.R
import imt3673.ass.groupexpenses.Utils.SingleExpense
import imt3673.ass.groupexpenses.Utils.convertAmountToString


class ExpenseAdapter: RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        private val imageLetter: TextView = itemView.findViewById(R.id.lbl_entry_prefix)
        private val name: TextView = itemView.findViewById(R.id.lbl_entry_name)
        private val description: TextView = itemView.findViewById(R.id.lbl_entry_description)
        private val amount: TextView = itemView.findViewById(R.id.lbl_entry_price)

        fun bind(item: SingleExpense) {
            imageLetter.text = item.person.first().toString()
            name.text = item.person
            description.text = item.description
            amount.text = convertAmountToString(item.amount)
        }

        companion object {
            fun from (parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.entry_expense, parent, false)
                return ViewHolder(view)
            }
        }
    }

    var data = listOf<SingleExpense>()
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