package com.eathealthyapp.is3261.eathealthyapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Food.db"
    }

    private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodTable.TABLE_NAME + " (" +
                    FoodTable.COLUMN_NAME + " TEXT PRIMARY KEY," +
                    FoodTable.COLUMN_CALORIES + " TEXT)"

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
        values.put(FoodTable.COLUMN_CALORIES, food.calories)

        db.insert(FoodTable.TABLE_NAME, null, values)
        return true
    }

    fun deleteFood(name: String): Boolean {
        val db = writableDatabase
        val selection = FoodTable.COLUMN_NAME + " LIKES ?"
        val selectionArgs = arrayOf(name)

        db.delete(FoodTable.TABLE_NAME, selection, selectionArgs)
        return true
    }

    fun readFood(name: String): ArrayList<FoodRecord> { val food = ArrayList<FoodRecord>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " +
                    FoodTable.TABLE_NAME + " WHERE " +
                    FoodTable.COLUMN_NAME + "=" + name + "", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var calories: Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                calories = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_CALORIES))
                food.add(FoodRecord(name, calories))
                cursor.moveToNext()
            } }
        return food
    }

    fun readAllFood(): ArrayList<FoodRecord> { val food = ArrayList<FoodRecord>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + FoodTable.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var name: String
        var calories: Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(FoodTable.COLUMN_NAME))
                calories = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_CALORIES))
                food.add(FoodRecord(name, calories))
                cursor.moveToNext()
            }
        }
        return food
    }
}