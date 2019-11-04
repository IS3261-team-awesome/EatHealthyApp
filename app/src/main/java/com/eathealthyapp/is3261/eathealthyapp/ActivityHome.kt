package com.eathealthyapp.is3261.eathealthyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class ActivityHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val btn = findViewById<Button>(R.id.btnTest)
        btn.setOnClickListener {
            val food = Food("Lemon Juice", 3.00f, 1000, 302, 13, 100)
            addFoodToList(food)
        }
    }

    fun addFoodToList(food: Food) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val foodListItem = FragmentFoodListItem()
        transaction.add(R.id.foodListContainer, foodListItem)
        transaction.commitNow()
        foodListItem.setNutrientInfo(food)

    }
}
