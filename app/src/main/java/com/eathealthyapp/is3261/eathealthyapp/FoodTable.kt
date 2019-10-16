package com.eathealthyapp.is3261.eathealthyapp

import android.provider.BaseColumns


class FoodTable : BaseColumns {
    companion object {
        val TABLE_NAME = "food"
        val COLUMN_NAME = "name"
        val COLUMN_CALORIES = "calories"
    }
}
