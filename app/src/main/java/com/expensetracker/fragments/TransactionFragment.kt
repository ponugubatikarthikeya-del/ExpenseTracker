package com.expensetracker.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.expensetracker.R
import com.expensetracker.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)
        val tabLayout = view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)

        val adapter = ViewPagerAdapter(requireActivity())
        val viewPager = view.findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            val tabView = LayoutInflater.from(requireContext())
                .inflate(R.layout.tab_layoutitem_bgdesign, null)

            val tabText = tabView.findViewById<TextView>(R.id.tabText)

            tabText.text = if (position == 0) "Income" else "Expenses"

            tab.customView = tabView

        }.attach()
        tabLayout.getTabAt(0)?.select()
        val tabView = tabLayout.getTabAt(0)?.customView

        tabView?.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.tab_bg
        )

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = tab.customView
                    ?.findViewById<TextView>(R.id.tabText)

                textView?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.tab_bg
                )
                textView?.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textView = tab.customView
                    ?.findViewById<TextView>(R.id.tabText)

                textView?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.defaulttab_bg
                )
                textView?.setTextColor(Color.BLACK)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return view
    }

}