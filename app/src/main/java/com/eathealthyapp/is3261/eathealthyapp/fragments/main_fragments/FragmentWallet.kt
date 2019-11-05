package com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments


import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.eathealthyapp.is3261.eathealthyapp.R

/**
 * A simple [Fragment] subclass.
 */
class FragmentWallet : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    private var myPreferences = "myPrefs"
    private var BALANCE = "balance"
    var currentBalance: Int = -1

    lateinit var balanceTextView : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get balance from saved data
        sharedPreferences = context!!.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        currentBalance = sharedPreferences.getInt(BALANCE, 0)

        // Set up widgets and their functionality
        balanceTextView = view.findViewById<TextView>(R.id.balance_textview)
        balanceTextView.setText(currentBalance.toString())

        val topupButton = view.findViewById<View>(R.id.top_up_button)
        topupButton.setOnClickListener {
            showTopupDialog()
        }

        val withdrawButton = view.findViewById<View>(R.id.withdraw_button)
        withdrawButton.setOnClickListener {
            showWithdrawDialog()
        }
    }

    private fun showTopupDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_wallet_topup)

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
                balanceTextView.setText(currentBalance.toString())

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

    private fun showWithdrawDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_wallet_withdraw)

        val withdrawEditText = dialog.findViewById<EditText>(R.id.withdraw_edittext)
        val confirmButton = dialog.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            if (withdrawEditText.text.toString() != "") {
                // Update current balance
                val withdrawAmount: Int = withdrawEditText.text.toString().toInt()

                val uncheckedCurrentBalance = currentBalance - withdrawAmount
                if (uncheckedCurrentBalance < 0) {
                    Toast.makeText(context, "Insufficient ammount to withdraw", Toast.LENGTH_SHORT).show()
                } else {
                    // Save data
                    currentBalance = uncheckedCurrentBalance
                    val editor = sharedPreferences.edit()
                    editor.putInt(BALANCE, currentBalance)
                    editor.apply()

                    // Change balance display
                    balanceTextView.setText(currentBalance.toString())

                    // Exit
                    dialog.dismiss()
                }
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
