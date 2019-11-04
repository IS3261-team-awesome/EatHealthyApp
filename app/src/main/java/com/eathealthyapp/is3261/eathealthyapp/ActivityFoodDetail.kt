package com.eathealthyapp.is3261.eathealthyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject

class ActivityFoodDetail : AppCompatActivity(), OnFoodParsed {


    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        foodDBHelper = DBHelper(this)

        val input = findViewById<EditText>(R.id.editTextfoodName)

        val tv = findViewById<TextView>(R.id.textViewfoodNutrition)


        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val foodName = input.text.toString()
            InternetJSON(this, this, foodName).execute()
        }

    }

    override fun OnFoodParsed(food: Food) {
        val tv = findViewById<TextView>(R.id.textViewfoodNutrition)
        tv.text = "parse successful -- " + food.getName() + " "+ food.getCalories()
    }

    fun getCalories(nutrientsObject: JSONObject): Int {
        val energy = nutrientsObject.getJSONObject("ENERC_KCAL")
        return energy.getString("quantity").toInt()
    }

    fun setTextView(data: String): String {
        val json = JSONObject(data)
        val nutrientsKCal = json.getJSONObject("totalNutrientsKCal")

        val energy = nutrientsKCal.getJSONObject("ENERC_KCAL")
        val energyQty = energy.getString("quantity")

        val protein = nutrientsKCal.getJSONObject("PROCNT_KCAL")
        val proteinQty = protein.getString("quantity")

        val fat = nutrientsKCal.getJSONObject("FAT_KCAL")
        val fatQty = fat.getString("quantity")

        val carbs = nutrientsKCal.getJSONObject("CHOCDF_KCAL")
        val carbsQty = carbs.getString("quantity")

        return "Calories: ${energyQty}\nProtein: ${proteinQty}\nFat: ${fatQty}\nCarbs: ${carbsQty}\n"
    }

    fun addFoodToDB(food: FoodRecord) {
        foodDBHelper.insertFood(food)
    }
}
