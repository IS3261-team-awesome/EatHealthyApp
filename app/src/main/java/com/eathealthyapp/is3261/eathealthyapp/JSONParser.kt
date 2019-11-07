package com.eathealthyapp.is3261.eathealthyapp

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import java.util.*

interface OnFoodParsed {
    fun OnFoodParsed(food: Food);
}

class JSONParser(private var c: Context,
                 private var listener: OnFoodParsed,
                 private var jsonData: String,
                 private var foodName: String) : AsyncTask<Void, Void, Boolean>() {
    private lateinit var food : Food

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg p0: Void?): Boolean {
        return parse()
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        if (result!!) {
            listener.OnFoodParsed(food)

        } else {
            Toast.makeText(c, "unable to parse", Toast.LENGTH_LONG).show()
            Toast.makeText(
                    c, "this is the data we were trying to parse : " +
                    jsonData, Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun parse(): Boolean {
        try {
            var foodObject = JSONObject(jsonData)

            food = Food(foodName,
                    5f, // TODO: set price
                    getCalories(foodObject).toInt(),
                    getProtein(foodObject).toInt(),
                    getCarbs(foodObject).toInt(),
                    getFat(foodObject).toInt(),
                    Calendar.getInstance().time.day,
                    Calendar.getInstance().time.month,
                    2019)
            // TODO: find a way to get year  added in a proper format



            return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }

    private fun getCalories(foodObject: JSONObject): String {
        val totalNutrientsObject = foodObject.getJSONObject("totalNutrientsKCal")
        val caloriesObject = totalNutrientsObject.getJSONObject("ENERC_KCAL")
        return caloriesObject.getString("quantity")
    }

    private fun getProtein(foodObject: JSONObject): String {
        val totalNutrientsObject = foodObject.getJSONObject("totalNutrientsKCal")
        val proteinObject = totalNutrientsObject.getJSONObject("PROCNT_KCAL")
        return proteinObject.getString("quantity")
    }

    private fun getCarbs(foodObject: JSONObject): String {
        val totalNutrientsObject = foodObject.getJSONObject("totalNutrientsKCal")
        val carbsObject = totalNutrientsObject.getJSONObject("CHOCDF_KCAL")
        return carbsObject.getString("quantity")
    }

    private fun getFat(foodObject: JSONObject): String {
        val totalNutrientsObject = foodObject.getJSONObject("totalNutrientsKCal")
        val fatObject = totalNutrientsObject.getJSONObject("FAT_KCAL")
        return fatObject.getString("quantity")
    }
}
