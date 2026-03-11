package com.expensetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AddIncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_income_activity)

        val etIncomeAmount = findViewById<EditText>(R.id.etIncomeAmount)
        val incomeCategoryView = findViewById<EditText>(R.id.IncomeCategoryEditText)
        val incomeBackBtn = findViewById<ImageButton>(R.id.incomeBackBtn)
        val incomeTodayView = findViewById<EditText>(R.id.IncomeDayEditText)
        val incomeNoteView = findViewById<EditText>(R.id.IncomeNoteEditText)
        val addType = findViewById<TextView>(R.id.AddIncome)
        val incomeBtnSave = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnIncomeSave)


        val type = intent.getStringExtra("type")
        addType.text = type

        incomeBtnSave.setOnClickListener {
            val incomeAmount = etIncomeAmount.text.toString()
            val incomeCategory = incomeCategoryView.text.toString()
            val incomeToday = incomeTodayView.text.toString()
            val incomeNote = incomeNoteView.text.toString()
            if (incomeAmount.isEmpty() || incomeCategory.isEmpty() || incomeToday.isEmpty() || incomeNote.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent()
            intent.putExtra("incomeAmount", incomeAmount)
            intent.putExtra("incomeCategory", incomeCategory)
            intent.putExtra("type",type)

            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        incomeBackBtn.setOnClickListener {
            finish()
        }

    }
}



