package com.eathealthyapp.is3261.eathealthyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ActivityPayment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val foodText = intent.getStringExtra("FOODITEM")

        val paymentInfoTV = findViewById<TextView>(R.id.paymentInfoTV)
        paymentInfoTV.setText(foodText)
        val confirmBtn = findViewById<Button>(R.id.confirmButton)
        confirmBtn.setOnClickListener {
            // Deduct from balance
            handlePayment()
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
    }
}
