package com.expensetracker.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expensetracker.DailyTransaction.DailyCategory
import com.expensetracker.DailyTransaction.DailyCategoryAdapter
import com.expensetracker.R
import com.expensetracker.SimpleTransactionPrefs

class HomeScreenFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DailyCategoryAdapter
    private val dailyCategory = ArrayList<DailyCategory>()

    private lateinit var emptyText: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        recyclerView = view.findViewById(R.id.dailyRecycleView)
        emptyText = view.findViewById(R.id.txtEmpty)


        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        // 🔥 LOAD SAVED DATA (AUTO CLEARS IF DAY CHANGED)
        dailyCategory.clear()
        dailyCategory.addAll(
            SimpleTransactionPrefs.load(requireContext())
        )
//
//        dailyCategory.add(
//            DailyCategory(
//                R.drawable.outline_transaction_on_24,
//                "Food",
//                "-₹45.00"
//            )
//        )


        adapter = DailyCategoryAdapter(dailyCategory)
        recyclerView.adapter = adapter

        refreshUi()

        return view
    }


    // 🔥 THIS METHOD IS CALLED FROM ACTIVITY
    fun addincomeToRecyclerView(category: String, amount: String ,typeName:String?) {

        val arrowIcon = if(typeName.equals("Add Income",true)){
          R.drawable.baseline_arrow_upward_24
        }else{
            R.drawable.baseline_arrow_downward_24

        }
        val arrowColor = if(typeName.equals("add income",true))
            ContextCompat.getColor(requireContext(),R.color.green)
        else
            ContextCompat.getColor(requireContext(),R.color.red)
        dailyCategory.add(
            DailyCategory(
                R.drawable.outline_transaction_on_24,
                category,
                if(typeName.equals("add income",true)) "+$$amount" else "-$$amount",
                arrowIcon,
                arrowColor
            )
        )

        adapter.notifyItemInserted(dailyCategory.size - 1)

        // 🔥 SAVE TO PREFS
        SimpleTransactionPrefs.save(requireContext(), dailyCategory)

        refreshUi()
    }
    // ✅ THIS METHOD WAS MISSING — NOW FIXED
    private fun refreshUi() {
        if (dailyCategory.isEmpty()) {
            emptyText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}