package com.expensetracker.DailyTransaction


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expensetracker.R

class DailyCategoryAdapter(
    private val list: MutableList<DailyCategory>
) : RecyclerView.Adapter<DailyCategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCategory: ImageView = itemView.findViewById(R.id.dailyExpensesDesignImageButton)
        val txtCategory: TextView = itemView.findViewById(R.id.dailyExpensesDesignCategory)
        val txtAmount: TextView = itemView.findViewById(R.id.dailyExpensesDesignAmountView)

        val imgArrow: ImageView = itemView.findViewById(R.id.dailyDesignArrowShow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.imgCategory.setImageResource(item.imageBtn)
        holder.txtCategory.text = item.category
        holder.txtAmount.text = item.amount
        holder.imgArrow.setImageResource(item.arrow)
        holder.imgArrow.setColorFilter(item.arrowColor) //tint the arrow
    }

    override fun getItemCount(): Int = list.size
}