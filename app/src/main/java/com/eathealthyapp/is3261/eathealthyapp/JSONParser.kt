package com.eathealthyapp.is3261.eathealthyapp

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JSONParser(private var c: Context,
                 private var jsonData: String,
                 private var myTextView: TextView) : AsyncTask<Void, Void, Boolean>() {
    private var foods = ArrayList<Food>()

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg p0: Void?): Boolean {
        return parse()
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)
        if (result!!) {
            myTextView.text = "parse successful -- " + foods.size +
                    " " + foods[foods.size - 1].getName() +
                    " " + foods[foods.size - 1].getCalories()
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
            var jo = JSONObject(jsonData)

            foods.clear()
            var food: Food

            val totalNutrientsKCal = jo.getJSONObject("totalNutrientsKCal")
            val energyKCal = totalNutrientsKCal.getJSONObject("ENERC_KCAL")
            val qty = energyKCal.getString("quantity")
//            val calories = totalNutrients.getJSONObject("ENERC_KCAL").getString("quantity")
//            val calories = jo.getString("calories")

            food = Food("apple", qty)
            foods.add(food)

            return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }
}
