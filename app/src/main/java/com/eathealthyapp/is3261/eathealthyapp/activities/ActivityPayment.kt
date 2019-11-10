package com.eathealthyapp.is3261.eathealthyapp.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.eathealthyapp.is3261.eathealthyapp.*
import com.eathealthyapp.is3261.eathealthyapp.utils.PriceCalculator

class ActivityPayment : AppCompatActivity(), OnFoodParsed {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        foodDBHelper = DBHelper(this)

        // TODO: details should not be passed as scanner but from selection screen
        // scannedText is in the format: foodDesciption-stallName-base price
        val scannedText = intent.getStringExtra("SCANNEDTEXT")
        val scannedTextParts = scannedText.split("-", limit = 3)
        val foodDescription = scannedTextParts[0]
        val stallName = scannedTextParts[1]
        val basePrice = scannedTextParts[2].toFloat()

        // Add to db first remove if cancel
        parseFoodAndAddToDb(foodDescription)

        // Get last food added (Food price now is currently a dummy value of 5)
        val lastAddedFoodRecord = foodDBHelper.readAllFood().last()
        lastAddedFoodRecord.price = basePrice

        // Update price
        val updatedPrice = PriceCalculator.getNewPrice(lastAddedFoodRecord, foodDBHelper)
        lastAddedFoodRecord.price = updatedPrice
        foodDBHelper.updateFoodPrice(lastAddedFoodRecord.name, updatedPrice)

        val foodNameTV = findViewById<TextView>(R.id.foodNameTV)
        foodNameTV.text = foodDescription
        val stallNameTV = findViewById<TextView>(R.id.stallNameTV)
        stallNameTV.text = stallName
        val priceTV = findViewById<TextView>(R.id.priceTV)
        priceTV.text = "$${String.format("%.2f", updatedPrice)}"
        val confirmBtn = findViewById<Button>(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            // Deduct from balance
            handlePayment(updatedPrice)

            // Go to food detail
            goToFoodDetailActivity(lastAddedFoodRecord.getFood())
            finish()
        }
        val cancelBtn = findViewById<Button>(R.id.cancel_button)
        cancelBtn.setOnClickListener {
            // Delete food from db
            foodDBHelper.deleteFood(lastAddedFoodRecord.name)

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
    fun parseFoodAndAddToDb(foodName: String) {
        // Automatically parse and add to db
        InternetJSON(this, this, foodName).execute()
    }

    override fun OnFoodParsed(food: Food) {
        val foodRecord = FoodRecord(
                food.getName(),
                food.getPrice(),
                food.getCalories(),
                food.getProtein(),
                food.getTotalCarbohydrate(),
                food.getTotalFat(),
                food.getDateAdded())
        addFoodToDB(foodRecord)
    }

    fun addFoodToDB(food: FoodRecord) {
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
