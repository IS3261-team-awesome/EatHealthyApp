package com.eathealthyapp.is3261.eathealthyapp.utils

import android.content.Context
import com.eathealthyapp.is3261.eathealthyapp.DBHelper
import com.eathealthyapp.is3261.eathealthyapp.Food
import com.eathealthyapp.is3261.eathealthyapp.FoodRecord
import com.eathealthyapp.is3261.eathealthyapp.utils.PriceCalculator.Companion.RECOMMENDED_PROTEIN_RATIO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MacroRatio(protein: Float, carbs: Float, fat: Float) {
    val protein = protein
    val carbs = carbs
    val fat = fat
}

class PriceCalculator {
    companion object {
        val RECOMMENDED_PROTEIN_RATIO = 0.2f
        val RECOMMENDED_CARBS_RATIO = 0.5f
        val RECOMMENDED_FAT_RATIO = 0.3f
        val RECOMMENDED_DAILY_SERVINGS = 3
        val EXCEEDED_SERVINGS_PENALTY = 0.3f

        fun getNewPrice(food: FoodRecord, foodDBHelper: DBHelper): Float {
            val foodList = getFoodListToday(foodDBHelper.readAllFood())

            val macroRatioToday = getMacroRatioToday(foodList)

            val proteinRatioDiff = getProteinRatioDiff(macroRatioToday.protein)
            val carbsRatioDiff = getCarbsRatioDiff(macroRatioToday.carbs)
            val fatRatioDiff = getFatRatioDiff(macroRatioToday.fat)

            return food.price *
                    (1 - proteinRatioDiff) *
                    (1 - carbsRatioDiff) *
                    (1 - fatRatioDiff) +
                    (foodDBHelper.getFoodCount(food.name).toFloat() - RECOMMENDED_DAILY_SERVINGS) *
                    EXCEEDED_SERVINGS_PENALTY
        }

        fun getMostExceededNutrient(food: FoodRecord, foodDBHelper: DBHelper): String {
            val foodList = getFoodListToday(foodDBHelper.readAllFood())

            val macroRatioToday = getMacroRatioToday(foodList)

            val proteinRatioDiff = getProteinRatioDiff(macroRatioToday.protein)
            val carbsRatioDiff = getCarbsRatioDiff(macroRatioToday.carbs)
            val fatRatioDiff = getFatRatioDiff(macroRatioToday.fat)

            var result = "protein"
            var max = proteinRatioDiff

            if (carbsRatioDiff > max) {
                max = carbsRatioDiff
                result = "carbohydrate"
            }

            if (fatRatioDiff > max) {
                result = "fat"
            }

            return result
        }
    }
}

private fun getProteinRatioDiff(protein: Float): Float {
    return PriceCalculator.RECOMMENDED_PROTEIN_RATIO - protein
}

private fun getCarbsRatioDiff(carbs: Float): Float {
    return PriceCalculator.RECOMMENDED_CARBS_RATIO - carbs
}

private fun getFatRatioDiff(fat: Float): Float {
    return PriceCalculator.RECOMMENDED_FAT_RATIO - fat
}

private fun getFoodListToday(foodRecords: ArrayList<FoodRecord>): ArrayList<FoodRecord> {
    var foodList = ArrayList<FoodRecord>()
    val dateFormat = SimpleDateFormat("dd MMM yyyy")
    val today = dateFormat.format(Calendar.getInstance().time)

    println("today: " + today)

    foodRecords.forEach {
        println("today: " + today + ", date: " + it.date)
        if (it.date == today) {
            foodList.add(it)
        }
    }
    return foodList
}

private fun getMacroRatioToday(foodList: ArrayList<FoodRecord>): MacroRatio {
    var totalProtein = 0
    var totalCarbs = 0
    var totalFat = 0

    foodList.forEach {
        totalProtein += it.protein
        totalCarbs += it.carbs
        totalFat += it.fat
    }
    return MacroRatio(
            totalProtein / getTotalCaloriesToday(foodList).toFloat(),
            totalCarbs / getTotalCaloriesToday(foodList).toFloat(),
            totalFat / getTotalCaloriesToday(foodList).toFloat()
    )
}

private fun getTotalCaloriesToday(foodList: ArrayList<FoodRecord>): Int {
    var calories = 0;
    foodList.forEach {
        calories += it.calories
    }
    return calories
}
