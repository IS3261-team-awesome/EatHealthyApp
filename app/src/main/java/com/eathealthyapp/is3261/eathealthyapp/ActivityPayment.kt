package com.eathealthyapp.is3261.eathealthyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ActivityPayment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val foodText = intent.getStringExtra("FOOD_TEXT")

        val paymentInfoTV = findViewById<TextView>(R.id.paymentInfoTV)
        paymentInfoTV.setText(foodText)
        val confirmBtn = findViewById<Button>(R.id.confirmButton)
        confirmBtn.setOnClickListener {
            // Deduct from balance
            handlePayment()
            // Notify main activity of new food item
            notifyNewFoodItem()
            // Return to main screen
            finish()
        }
        val cancelBtn = findViewById<Button>(R.id.cancelButton)
        cancelBtn.setOnClickListener {
            // Return to main screen
            finish()
        }
    }

    fun handlePayment() {
        Toast.makeText(this, "Successfully paid!", Toast.LENGTH_LONG).show()
        // Minus from current balance
    }

    fun notifyNewFoodItem() {
        // Do an intent to put data, and change main activity to startActivityForResult()
    }
}
