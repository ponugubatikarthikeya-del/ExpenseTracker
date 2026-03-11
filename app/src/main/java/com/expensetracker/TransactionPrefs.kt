package com.expensetracker


import android.content.Context
import com.expensetracker.DailyTransaction.DailyCategory
import com.expensetracker.R
import java.text.SimpleDateFormat
import java.util.*

object SimpleTransactionPrefs {

    private const val PREF_NAME = "daily_cards"
    private const val KEY_DATA = "cards"
    private const val KEY_DATE = "date"

    private fun today(): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun save(context: Context, list: List<DailyCategory>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val stringList = list.map { item ->
            "${item.category}|${item.amount}|${item.arrow}|${item.arrowColor}"
        }
        prefs.edit()
            .putString(KEY_DATA, stringList.joinToString(";"))
            .putString(KEY_DATE, today())
            .apply()

//
//        val editor = prefs.edit()
//        val stringList = list.map { item ->
//            "${item.category}|${item.amount}|${item.arrow}|${item.arrowColor}"
//        }
//        editor.putString(KEY_DATA, stringList.joinToString(";"))
//        editor.putString(KEY_DATE, today())
//        editor.apply()
//
//        }



//        val sb = StringBuilder()
//        for (item in list) {
//            sb.append(item.category)
//                .append("|")
//                .append(item.amount)
//                .append(";")
//        }
//
//        prefs.edit()
//            .putString(KEY_DATA, sb.toString())
//            .putString(KEY_DATE, today())
//            .apply()
    }

    fun load(context: Context): ArrayList<DailyCategory> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // 🔥 CLEAR IF DAY CHANGED
        val savedDate = prefs.getString(KEY_DATE, null)
        if (savedDate != today()) {
            prefs.edit().clear().apply()
            return ArrayList()
        }

        val data = prefs.getString(KEY_DATA, "") ?: ""
        if (data.isEmpty()) return ArrayList()

        val list = ArrayList<DailyCategory>()
        val cards = data.split(";")

        for (card in cards) {
            if (card.contains("|")) {
                val parts = card.split("|")
                if (parts.size == 4) {
                    list.add(
                        DailyCategory(
                            R.drawable.outline_transaction_on_24,
                            parts[0],
                            parts[1],
                            parts[2].toInt(),
                            parts[3].toInt()
                        )
                    )
                }
            }
        }
        return list
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}