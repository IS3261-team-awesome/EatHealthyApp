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
    var currentBalance = -1f

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
        currentBalance = sharedPreferences.getFloat(BALANCE, 0f)

        // Set up widgets and their functionality
        balanceTextView = view.findViewById<TextView>(R.id.balance_textview)
        setBalanceText(currentBalance)

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
        dialog.window.decorView.setBackgroundResource(android.R.color.transparent)

        val topupEditText = dialog.findViewById<EditText>(R.id.topup_edittext)
        val confirmButton = dialog.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            if (topupEditText.text.toString() != "") {
                // Update current balance
                val topupAmount: Float = topupEditText.text.toString().toFloat()
                currentBalance += topupAmount

                // Save data
                val editor = sharedPreferences.edit()
                editor.putFloat(BALANCE, currentBalance)
                editor.apply()

                // Change balance display
                setBalanceText(currentBalance)

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
        dialog.window.decorView.setBackgroundResource(android.R.color.transparent)

        val withdrawEditText = dialog.findViewById<EditText>(R.id.withdraw_edittext)
        val confirmButton = dialog.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener {
            if (withdrawEditText.text.toString() != "") {
                // Update current balance
                val withdrawAmount: Float = withdrawEditText.text.toString().toFloat()

                val uncheckedCurrentBalance = currentBalance - withdrawAmount
                if (uncheckedCurrentBalance < 0) {
                    Toast.makeText(context, "Insufficient amount", Toast.LENGTH_SHORT).show()
                } else {
                    // Save data
                    currentBalance = uncheckedCurrentBalance
                    val editor = sharedPreferences.edit()
                    editor.putFloat(BALANCE, currentBalance)
                    editor.apply()

                    // Change balance display
                    setBalanceText(currentBalance)

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

    fun setBalanceText(currentBalance: Float) {
        balanceTextView.setText("$" + currentBalance.toString())
    }
}
