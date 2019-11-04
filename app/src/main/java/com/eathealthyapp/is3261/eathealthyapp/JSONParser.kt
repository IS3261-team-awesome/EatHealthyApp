package com.eathealthyapp.is3261.eathealthyapp

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject

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
            var jo = JSONObject(jsonData)

            val totalNutrientsKCal = jo.getJSONObject("totalNutrientsKCal")
            val energyKCal = totalNutrientsKCal.getJSONObject("ENERC_KCAL")
            val qty = energyKCal.getString("quantity")
//            val calories = totalNutrients.getJSONObject("ENERC_KCAL").getString("quantity")
//            val calories = jo.getString("calories")

            food = Food(foodName, qty)

            return true
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }
}
