package com.eathealthyapp.is3261.eathealthyapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.eathealthyapp.is3261.eathealthyapp.*
import java.text.SimpleDateFormat
import java.util.*

class ActivityPayment : AppCompatActivity(), OnFoodParsed {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        foodDBHelper = DBHelper(this)

        // scannedText is in the format: foodDesciption-stallName-base price
        val scannedText = intent.getStringExtra("SCANNEDTEXT")
        val scannedTextParts = scannedText.split("-", limit = 2)
        val foodDescription = scannedTextParts[0]
        val stallName = scannedTextParts[1]
        println(foodDescription)
        println(stallName)


        val paymentInfoTV = findViewById<TextView>(R.id.paymentInfoTV)
        paymentInfoTV.text = foodDescription
        val confirmBtn = findViewById<Button>(R.id.confirmButton)
        confirmBtn.setOnClickListener {
            // Deduct from balance
            handlePayment()
            // Notify main activity of new food item
            parseFoodAndAddToDb(foodDescription)
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
        // TODO: Minus from current balance
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
                food.getDayAdded(),
                food.getMonthAdded(),
                food.getYearAdded())
        addFoodToDB(foodRecord)

        // TODO: go to food detail (jolyn)
    }

    fun addFoodToDB(food: FoodRecord) {
        Log.i("add", food.name)
        foodDBHelper.insertFood(food)
    }
}
