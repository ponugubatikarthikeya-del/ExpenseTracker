package com.expensetracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expensetracker.fragments.ExpensesFragment
import com.expensetracker.fragments.IncomeFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncomeFragment()
            1 -> ExpensesFragment()
            else -> IncomeFragment()
        }
    }
}