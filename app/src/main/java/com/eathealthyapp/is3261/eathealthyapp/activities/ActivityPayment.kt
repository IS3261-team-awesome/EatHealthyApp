package com.eathealthyapp.is3261.eathealthyapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.eathealthyapp.is3261.eathealthyapp.*
import com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments.FragmentHome

class ActivityPayment : AppCompatActivity(), OnFoodParsed {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        foodDBHelper = DBHelper(this)

        // TODO: details should not be passed as scanned text but from selection screen
        // scannedText is in the format: foodDesciption-stallName-base price
        val scannedText = intent.getStringExtra("SCANNEDTEXT")
        val scannedTextParts = scannedText.split("-", limit = 3)
        val foodDescription = scannedTextParts[0]
        val stallName = scannedTextParts[1]
        val basePrice = scannedTextParts[2]

        val foodNameTV = findViewById<TextView>(R.id.foodNameTV)
        foodNameTV.text = foodDescription
        val stallNameTV = findViewById<TextView>(R.id.stallNameTV)
        stallNameTV.text = stallName
        val priceTV = findViewById<TextView>(R.id.priceTV)
        priceTV.text = basePrice
        val confirmBtn = findViewById<Button>(R.id.confirm_button)
        confirmBtn.setOnClickListener {
            // Deduct from balance
            handlePayment()
            // Notify main activity of new food item
            parseFoodAndAddToDb(foodDescription)

            // TODO: parseFoodAndAddToDb is running while finish is called so onresume() in FragmentHome runes before food is added to db thus when update foodlist is called the new food is not registered
            // TODO: end activity and go to food detail then from there back to main activity (jolyn)
            finish()
        }
        val cancelBtn = findViewById<Button>(R.id.cancel_button)
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
                food.getDateAdded())
        addFoodToDB(foodRecord)
    }

    fun addFoodToDB(food: FoodRecord) {
        Log.i("add", food.name)
        foodDBHelper.insertFood(food)
    }
}
