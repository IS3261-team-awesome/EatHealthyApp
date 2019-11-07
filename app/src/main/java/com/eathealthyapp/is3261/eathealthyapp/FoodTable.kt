package com.eathealthyapp.is3261.eathealthyapp

import android.provider.BaseColumns

class FoodTable : BaseColumns {
    companion object {
        val TABLE_NAME = "food"
        val COLUMN_NAME = "name"
        val COLUMN_PRICE = "price"
        val COLUMN_CALORIES = "calories"
        val COLUMN_PROTEIN = "protein"
        val COLUMN_CARBS = "carbs"
        val COLUMN_FAT = "fat"
        val COLUMN_DAY = "day"
        val COLUMN_MONTH = "month"
        val COLUMN_YEAR = "year"
        val COLUMN_ID = "id"
    }
}
