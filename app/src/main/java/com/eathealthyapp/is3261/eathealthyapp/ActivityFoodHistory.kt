package com.eathealthyapp.is3261.eathealthyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_food_history.*
import kotlinx.android.synthetic.main.activity_food_history.view.*

class ActivityFoodHistory : AppCompatActivity() {

    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_history)

        foodDBHelper = DBHelper(this)
        displayFood()
    }

    fun displayFood() {
        var food = foodDBHelper.readAllFood()
        var str: String? = null

        food.forEach {
            str = str + it.name + ": " + it.calories.toString() + " calories" + "\n"
        }

        this.scrollView.textViewFoodHistory.text = str
    }
}
