package com.eathealthyapp.is3261.eathealthyapp

class FoodRecord(val name: String,
                 var price: Float,
                 val calories: Int,
                 val protein: Int,
                 val carbs: Int,
                 val fat: Int,
                 val date: String) {
    fun getFood(): Food {
        return Food(name, price, calories, protein, carbs, fat, date)
    }
}