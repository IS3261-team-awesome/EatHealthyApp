package com.eathealthyapp.is3261.eathealthyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button


class ActivityHome : AppCompatActivity() {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        foodDBHelper = DBHelper(this)
        populateFoodList()

        val btn = findViewById<Button>(R.id.btnTest)
        btn.setOnClickListener {
            // TODO: add food from qr
            val food = Food("Lemon Juice", 3.00f, 1000, 302, 13, 100)
            addFoodToList(food)
        }
    }

    fun populateFoodList() {
        var foodRecord = foodDBHelper.readAllFood()

        var name: String
        var price: Float
        var calories: Int
        var protein: Int
        var carbs: Int
        var fat: Int

        foodRecord.forEach {
            name = it.name
            price = it.price
            calories = it.calories
            protein = it.protein
            carbs = it.carbs
            fat = it.fat

            val food = Food(name, price, calories, protein, carbs, fat)
            addFoodToList(food)
        }
    }

    fun addFoodToList(food: Food) {
        val foodListItem = FragmentFoodListItem()

        val bundle = Bundle()
        bundle.putString("name", food.getName())
        bundle.putFloat("price", food.getPrice())
        bundle.putInt("calories", food.getCalories())
        bundle.putInt("protein", food.getProtein())
        bundle.putInt("carbs", food.getTotalCarbohydrate())
        bundle.putInt("fat", food.getTotalFat())

        foodListItem.setArguments(bundle)

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.foodListContainer, foodListItem)
        transaction.commit()
    }
}
