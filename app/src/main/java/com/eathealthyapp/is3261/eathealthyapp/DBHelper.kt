package com.eathealthyapp.is3261.eathealthyapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.eathealthyapp.is3261.eathealthyapp.utils.PriceCalculator

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Food.db"
    }

    private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodTable.TABLE_NAME + " (" +
                    FoodTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FoodTable.COLUMN_NAME + " TEXT," +
                    FoodTable.COLUMN_PRICE + " TEXT," +
                    FoodTable.COLUMN_CALORIES + " TEXT," +
                    FoodTable.COLUMN_PROTEIN + " TEXT," +
                    FoodTable.COLUMN_CARBS + " TEXT," +
                    FoodTable.COLUMN_FAT + " TEXT," +
                    FoodTable.COLUMN_DATE + " TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FoodTable.TABLE_NAME

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertFood(food: FoodRecord): Boolean {
        val db = writableDatabase
        val values = ContentValues()

        values.put(FoodTable.COLUMN_NAME, food.name)
        values.put(FoodTable.COLUMN_PRICE, food.price)
        values.put(FoodTable.COLUMN_CALORIES, food.calories)
        values.put(FoodTable.COLUMN_PROTEIN, food.protein)
        values.put(FoodTable.COLUMN_CARBS, food.carbs)
        values.put(FoodTable.COLUMN_FAT, food.fat)
        values.put(FoodTable.COLUMN_DATE, food.date)

        db.insert(FoodTable.TABLE_NAME, null, values)


        // update price

        val updatedPrice = PriceCalculator.getNewPrice(food, this)
        println("NEW PRICE: "+  updatedPrice)
        updateFoodPrice(food.name, updatedPrice)

        return true
    }

    fun deleteFood(name: String): Boolean {
        val db = writableDatabase
        val selection = FoodTable.COLUMN_NAME + " LIKES ?"
        val selectionArgs = arrayOf(name)

        db.delete(FoodTable.TABLE_NAME, selection, selectionArgs)
        return true
    }

    fun readFood(name: String): ArrayList<FoodRecord> {
        val food = ArrayList<FoodRecord>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " +
                    FoodTable.TABLE_NAME + " WHERE " +
                    FoodTable.COLUMN_NAME +
                    "='" + name + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var price: Float
        var calories: Int
        var protein: Int
        var carbs: Int
        var fat: Int
        var date: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                price = cursor.getFloat(cursor.getColumnIndex(FoodTable.COLUMN_PRICE))
                calories = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_CALORIES))
                protein = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_PROTEIN))
                carbs = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_CARBS))
                fat = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_FAT))
                date = cursor.getString(cursor.getColumnIndex(FoodTable.COLUMN_DATE))

                food.add(FoodRecord(
                        name,
                        price,
                        calories,
                        protein,
                        carbs,
                        fat,
                        date))

                cursor.moveToNext()
            }
        }
        return food
    }

    fun readAllFood(): ArrayList<FoodRecord> {
        val food = ArrayList<FoodRecord>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + FoodTable.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var name: String
        var price: Float
        var calories: Int
        var protein: Int
        var carbs: Int
        var fat: Int
        var date: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(FoodTable.COLUMN_NAME))
                price = cursor.getFloat(cursor.getColumnIndex(FoodTable.COLUMN_PRICE))
                calories = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_CALORIES))
                protein = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_PROTEIN))
                carbs = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_CARBS))
                fat = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_FAT))
                date = cursor.getString(cursor.getColumnIndex(FoodTable.COLUMN_DATE))

                food.add(FoodRecord(
                        name,
                        price,
                        calories,
                        protein,
                        carbs,
                        fat,
                        date))

                cursor.moveToNext()
            }
        }
        return food
    }

    fun getFoodCount(name: String): Int {
        return readFood(name).size
    }

    fun updateFoodPrice(name: String, newPrice: Float) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(FoodTable.COLUMN_PRICE, newPrice)
        db.update(FoodTable.TABLE_NAME, cv, "${FoodTable.COLUMN_NAME} = '$name'", null)

        val list = readAllFood()
        list.forEach {
            print(it.price)
            println(it.name)
        }
    }
}