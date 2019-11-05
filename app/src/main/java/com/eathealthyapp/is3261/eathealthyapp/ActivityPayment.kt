package com.eathealthyapp.is3261.eathealthyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ActivityPayment : AppCompatActivity(), OnFoodParsed  {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        foodDBHelper = DBHelper(this)

        val foodText = intent.getStringExtra("FOOD_TEXT")

        val paymentInfoTV = findViewById<TextView>(R.id.paymentInfoTV)
        paymentInfoTV.setText(foodText)
        val confirmBtn = findViewById<Button>(R.id.confirmButton)
        confirmBtn.setOnClickListener {
            // Deduct from balance
            handlePayment()
            // Notify main activity of new food item
//            notifyNewFoodItem()
            getFoodItemInfo(" 1 apple")
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

    // foodName format : 1 apple
    fun getFoodItemInfo(foodName: String) {
        InternetJSON(this, this, foodName).execute()
    }

    fun notifyNewFoodItem() {
        // Do an intent to put data, and change main activity to startActivityForResult()
        // Call API (Inte
        // addFoodToDB()
    }

    override fun OnFoodParsed(food: Food) {
        val foodRecord = FoodRecord(
                food.getName(),
                food.getPrice(),
                food.getCalories(),
                food.getProtein(),
                food.getTotalCarbohydrate(),
                food.getTotalFat())
        addFoodToDB(foodRecord)

        // TODO: go to food detail (jolyn)
    }

    fun addFoodToDB(food: FoodRecord) {
        Log.i("add", food.name)
        foodDBHelper.insertFood(food)
    }
}
