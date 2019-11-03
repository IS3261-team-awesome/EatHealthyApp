package com.eathealthyapp.is3261.eathealthyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class ActivityFoodDetail : AppCompatActivity(), OnFoodParsed {
    val colorProtein = "#B477FD"
    val colorCarbs = "#5CECC9"
    val colorFat = "#FFD15A"
    val colorNone = "#EBEBEB"

    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        foodDBHelper = DBHelper(this)

        val input = findViewById<EditText>(R.id.editTextfoodName)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val query = input.text.toString()
            InternetJSON(this, this, query).execute()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    override fun OnFoodParsed(food: Food) {

        // temp
        val tv = findViewById<TextView>(R.id.textViewfoodNutrition)
        tv.text = "parse successful -- " +
                food.getName() + " " +
                food.getCalories() + " " +
                food.getTotalFat() + " " +
                food.getProtein() + " " +
                food.getTotalCarbohydrate()

        // temp
        val foodNameTitle = findViewById<TextView>(R.id.tvfoodName)
        foodNameTitle.text = food.getName()

        // set price
        val tvPrice = findViewById<TextView>(R.id.textPrice)
        tvPrice.text = "$${String.format("%.2f", food.getPrice())}"

        val fragmentManager = supportFragmentManager
        val foodChartFragment = fragmentManager.findFragmentById(R.id.foodChartDetail) as? FoodChart
        foodChartFragment?.drawDonutChart(food)

//        drawDonutChart(food)

        // set protein
        // set carbs
        // set fat
    }

    // TODO: add to db
    fun addFoodToDB(food: FoodRecord) {
        foodDBHelper.insertFood(food)
    }
}
