package com.expensetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.expensetracker.fragments.HomeScreenFragment
import com.expensetracker.fragments.TransactionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView


class DefaultScreen: AppCompatActivity() {
    private var currentHomeFragmnet : HomeScreenFragment ? =null //track current fragment


    // ✅ REGISTER LAUNCHER (GLOBAL, NOT INSIDE onCreateView)
    private val addIncomeLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == Activity.RESULT_OK) {

                val data = result.data
                val incomeAmount = data?.getStringExtra("incomeAmount")
                val incomeCategory = data?.getStringExtra("incomeCategory")
                val type = data?.getStringExtra("type")

                if (!incomeAmount.isNullOrEmpty() && !incomeCategory.isNullOrEmpty()) {
                    currentHomeFragmnet?.addincomeToRecyclerView(incomeCategory, incomeAmount,type)

                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.default_screen)

        // Default fragment
        loadFragment(HomeScreenFragment())

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val btnAdd = findViewById<MaterialCardView>(R.id.btnAdd)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> loadFragment(HomeScreenFragment())
                R.id.transaction -> loadFragment(TransactionFragment())
            }
            true
        }

        // CENTER + BUTTON CLICK
        btnAdd.setOnClickListener {
            showAddBottomSheet()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        if(fragment is HomeScreenFragment){
            currentHomeFragmnet = fragment //set reference when loading Home
        }
    }

    private fun showAddBottomSheet() {

        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_swipe_cardview, null)

        val btnIncome = view.findViewById<LinearLayout>(R.id.btnAddIncome)
        val btnExpense = view.findViewById<LinearLayout>(R.id.btnAddExpense)
        val swipeAddIncomeTextview = view.findViewById<TextView>(R.id.swipeAddIncomeTextview)
        val swipeAddExpensesTextview = view.findViewById<TextView>(R.id.swipeAddExpensesTextview)

        btnIncome.setOnClickListener {
            bottomSheetDialog.dismiss()
//            val item = swipeAddIncomeTextview.text.toString()
            val intent= Intent(this, AddIncomeActivity::class.java)
            intent.putExtra("type",swipeAddIncomeTextview.getText().toString())
            addIncomeLauncher.launch( intent)
            overridePendingTransition(0,0)
        }

        btnExpense.setOnClickListener {
            bottomSheetDialog.dismiss()
//            val item = swipeAddExpensesTextview.text.toString()
                val intent = Intent(this, AddIncomeActivity::class.java)
                intent.putExtra("type", swipeAddExpensesTextview.getText().toString())
                addIncomeLauncher.launch(intent)
            overridePendingTransition(0,0)
        }
        // full width

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let{
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                it.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                it.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }
        bottomSheetDialog.show()
    }

    override fun finish(){
        super.finish()
        overridePendingTransition(0,0)
    }


}
