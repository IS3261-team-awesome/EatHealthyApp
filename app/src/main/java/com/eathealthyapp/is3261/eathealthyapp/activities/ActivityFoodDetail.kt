package com.eathealthyapp.is3261.eathealthyapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.eathealthyapp.is3261.eathealthyapp.*
import com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments.FragmentFoodChart


class ActivityFoodDetail : AppCompatActivity() {
    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        foodDBHelper = DBHelper(this)

        val food = intent.getParcelableExtra<Food>("food")

        // set up toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener{
            finish()
            // TODO: remove after ocnverting this to fragment
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        // set food name
        val tvFoodName = findViewById<TextView>(R.id.tvfoodName)
        tvFoodName.text = food.getName()

        // set price
        val tvPrice = findViewById<TextView>(R.id.tvPrice)
        println(food.getPrice().toString())
        tvPrice.text = "$${String.format("%.2f", food.getPrice())}"

        // draw chart
        val fragmentManager = supportFragmentManager
        val foodChartFragement = fragmentManager.findFragmentById(R.id.foodChartDetail) as? FragmentFoodChart
        foodChartFragement?.setNutrientInfo(food)

        // set serving count
        val count = foodDBHelper.getFoodCount(food.getName())
        val tvServings = findViewById<TextView>(R.id.tvServings)
        var servingsText = "servings"
        if (count == 1) {
            servingsText = "serving"
        }
        tvServings.text = "$count $servingsText consumed today"
    }
}
