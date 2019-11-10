package com.eathealthyapp.is3261.eathealthyapp.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.eathealthyapp.is3261.eathealthyapp.*
import com.eathealthyapp.is3261.eathealthyapp.utils.PriceCalculator
import com.eathealthyapp.is3261.eathealthyapp.utils.QRParser

class ActivityPayment : AppCompatActivity(), OnFoodParsed {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        foodDBHelper = DBHelper(this)

        // TODO: details should not be passed as scanner but from selection screen
        // scannedText is in the format: foodDesciption-stallName-base price
        val scannedText = intent.getStringExtra("SCANNEDTEXT")
        val stallName = QRParser.getFoodStall(scannedText)

        val stallNameTV = findViewById<TextView>(R.id.stallNameTV)
        stallNameTV.text = stallName

        parseFoodAndAddToDb(scannedText)

        // Get last food added (Food price now is currently a dummy value of 5)

        val cancelBtn = findViewById<Button>(R.id.cancel_button)
        cancelBtn.setOnClickListener {
            // Return to main screen
            finish()
        }
    }


    fun handlePayment(updatedPrice: Float) {
        Toast.makeText(this, "Successfully paid!", Toast.LENGTH_LONG).show()
        // TODO: Minus from current balance
        // Deduct from balance
        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val currentBalance = sharedPreferences.getFloat("balance", 0f)
        val newBalance = currentBalance - updatedPrice
        val editor = sharedPreferences.edit()
        editor.putFloat("balance", newBalance)
        editor.apply()
    }

    // foodName format : 1 apple
    fun parseFoodAndAddToDb(scannedText: String) {
        // Automatically parse and add to db
        Log.i("123", "3----------------------------------")
        InternetJSON(this, this, scannedText).execute()
    }

    override fun OnFoodParsed(food: Food) {
        Log.i("123", "2----------------------------------")

        Log.i("fooddddddddd", food.getName())

        val foodRecord = FoodRecord(
                food.getName(),
                food.getPrice(),
                food.getCalories(),
                food.getProtein(),
                food.getTotalCarbohydrate(),
                food.getTotalFat(),
                food.getDateAdded())

        Log.i("priceeeeeeee", food.getPrice().toString())


        // Update price
        val updatedPrice = PriceCalculator.getNewPrice(foodRecord, foodDBHelper)
        foodRecord.price = updatedPrice
        Log.i("priceeeeeeee", updatedPrice.toString())
        // Update text views
        val foodNameTV = findViewById<TextView>(R.id.foodNameTV)

        foodNameTV.text = food.getName()
        val priceTV = findViewById<TextView>(R.id.priceTV)
        priceTV.text = "$${String.format("%.2f", updatedPrice)}"
        val confirmBtn = findViewById<Button>(R.id.confirm_button)

        val sharedPreferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val currentBalance = sharedPreferences.getFloat("balance", 0f)

        confirmBtn.setOnClickListener {
            if (currentBalance >= updatedPrice) {
                // Add to db
                addFoodToDB(foodRecord)

                // Deduct from balance
                handlePayment(updatedPrice)

                // Go to food detail
                goToFoodDetailActivity(foodRecord.getFood())
                finish()
            } else {
                Toast.makeText(this,
                        "Insufficient balance, please top up first.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun addFoodToDB(food: FoodRecord) {
        Log.i("123", "1----------------------------------")
        Log.i("add", food.name)
        foodDBHelper.insertFood(food)
    }

    fun goToFoodDetailActivity(food: Food) {
        val bundle = Bundle()
        bundle.putParcelable("food", food)
        val intent = Intent(this, ActivityFoodDetail::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
