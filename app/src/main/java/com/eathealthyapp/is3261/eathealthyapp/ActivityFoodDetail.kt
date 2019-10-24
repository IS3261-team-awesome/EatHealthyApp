package com.eathealthyapp.is3261.eathealthyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject

class ActivityFoodDetail : AppCompatActivity() {
    val APP_ID = "0377a2d4"
    val APP_KEY = "b41e77e4438bcd0244672cbbc943ff8d"

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
            InternetJSON(this, foodName, tv).execute()
        }

    }

    fun fetchNutritionInfo(foodName: String) {
        val url = "https://api.edamam.com/api/nutrition-data?app_id=${APP_ID}&app_key=${APP_KEY}&ingr=1%20${foodName}"
        val textView = findViewById<TextView>(R.id.textViewfoodNutrition)

//        val stringRequest = StringRequest(Request.Method.GET, url,
//                Response.Listener<String> { response ->
//                    val json = JSONObject(response)
//                    val nutrientsObject = json.getJSONObject("totalNutrientsKCal")
//                    val calories = getCalories(nutrientsObject)
//
//                    textView.text = setTextView(response)
//                    addFoodToDB(FoodRecord(foodName, calories))
//                },
//                Response.ErrorListener {
//                    textView.text = "That didn't work!"
//                }
//        )
//
//        queue.add(stringRequest)
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
