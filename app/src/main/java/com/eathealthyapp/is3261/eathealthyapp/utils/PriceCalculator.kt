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

            val macroRatioToday = getMacroRatioToday(foodList, food)

            val proteinRatioDiff = getProteinRatioDiff(macroRatioToday.protein)
            val carbsRatioDiff = getCarbsRatioDiff(macroRatioToday.carbs)
            val fatRatioDiff = getFatRatioDiff(macroRatioToday.fat)

            var foodCountScaling = foodDBHelper.getFoodCount(food.name).toFloat() - RECOMMENDED_DAILY_SERVINGS

            if (foodCountScaling <= 0) {
                foodCountScaling = 0f
            }

            return food.price *
                    (1 - proteinRatioDiff) *
                    (1 - carbsRatioDiff) *
                    (1 - fatRatioDiff) +
                    foodCountScaling *
                    EXCEEDED_SERVINGS_PENALTY
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

private fun getMacroRatioToday(foodList: ArrayList<FoodRecord>, food: FoodRecord): MacroRatio {
    val totalCaloriesToday = getTotalCaloriesToday(foodList).toFloat()

    if (totalCaloriesToday == 0f) {
        return MacroRatio(
                food.protein / food.calories.toFloat(),
                food.carbs / food.calories.toFloat(),
                food.fat/ food.calories.toFloat())
    }

    var totalProtein = 0
    var totalCarbs = 0
    var totalFat = 0
    foodList.forEach {
        totalProtein += it.protein
        totalCarbs += it.carbs
        totalFat += it.fat
    }
    return MacroRatio(
            totalProtein / (totalCaloriesToday + food.calories),
            totalCarbs / (totalCaloriesToday + food.calories),
            totalFat / (totalCaloriesToday + food.calories)
    )
}

private fun getTotalCaloriesToday(foodList: ArrayList<FoodRecord>): Int {
    var calories = 0;
    foodList.forEach {
        calories += it.calories
    }
    return calories
}
