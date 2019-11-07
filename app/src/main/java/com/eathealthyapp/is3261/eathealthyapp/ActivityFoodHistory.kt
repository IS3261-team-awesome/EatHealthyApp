package com.eathealthyapp.is3261.eathealthyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_food_history.*
import kotlinx.android.synthetic.main.activity_food_history.view.*

class ActivityFoodHistory : AppCompatActivity(), OnFoodParsed {

    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_history)

        foodDBHelper = DBHelper(this)
        displayFood()

        val input = findViewById<EditText>(R.id.inputTest)
        val btnAddFood = findViewById<Button>(R.id.btnAddFoodTest)
        btnAddFood.setOnClickListener {
            val query = input.text.toString()
            InternetJSON(this, this, query).execute()
        }
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
        displayFood()
    }

    fun displayFood() {
        var food = foodDBHelper.readAllFood()
        var str: String? = null

        food.forEach {
            str = str +
                    it.name + ": " +
                    " price-" + it.price.toString() +
                    " calories-" + it.calories.toString() +
                    " protein-" + it.protein.toString() +
                    " carbs-" + it.carbs.toString() +
                    " fat-" + it.fat.toString() +
                    "\n"
        }

        this.scrollView.textViewFoodHistory.text = str
    }

    fun addFoodToDB(food: FoodRecord) {
        Log.i("add", food.name)
        foodDBHelper.insertFood(food)
    }

    fun deleteFood(food: FoodRecord) {

    }
}
