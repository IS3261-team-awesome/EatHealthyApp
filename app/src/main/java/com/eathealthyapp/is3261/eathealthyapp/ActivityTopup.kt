package com.eathealthyapp.is3261.eathealthyapp

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_topup.*


class ActivityTopup : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var BALANCE = "balance"
    var currentBalance: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup)

        // Get balance from saved data
        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        currentBalance = sharedPreferences.getInt(BALANCE, 0)

        // Set up widgets and their functionality
        val balanceTextView = findViewById<TextView>(R.id.balance_textview)
        balanceTextView.setText(currentBalance.toString())

        val topupButton = findViewById<View>(R.id.top_up_button)
        topupButton.setOnClickListener {
            showTopupDialog()
        }
    }

    private fun showTopupDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_topup_page)

        val topupEditText = dialog.findViewById<EditText>(R.id.topup_edittext)
        val confirmButton = dialog.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            if (topupEditText.text.toString() != "") {
                // Update current balance
                val topupAmount: Int = topupEditText.text.toString().toInt()
                currentBalance += topupAmount

                // Save data
                val editor = sharedPreferences.edit()
                editor.putInt(BALANCE, currentBalance)
                editor.apply()

                // Change balance display
                balance_textview.setText(currentBalance.toString())

                // Exit
                dialog.dismiss()
            }
        }

        val exitDialogButton = dialog.findViewById<Button>(R.id.exit_dialog_button)
        exitDialogButton.setOnClickListener {
            // Exit
            dialog.dismiss()
        }

        dialog.show()
    }
}
