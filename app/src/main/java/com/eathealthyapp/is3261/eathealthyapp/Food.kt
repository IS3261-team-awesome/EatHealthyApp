package com.eathealthyapp.is3261.eathealthyapp

import android.os.Parcel
import android.os.Parcelable

class Food (
        private var name: String,
        private var price: Float,
        private var calories: Int,
        private var totalFat: Int,
        private var totalCarbohydrate: Int,
        private var protein: Int,
        private var date: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Food> {
            override fun createFromParcel(parcel: Parcel) = Food(parcel)
            override fun newArray(size: Int) = arrayOfNulls<Food>(size)
        }
    }

    constructor(parcel: Parcel): this (
            name = parcel.readString(),
            price = parcel.readFloat(),
            calories = parcel.readInt(),
            totalFat = parcel.readInt(),
            totalCarbohydrate = parcel.readInt(),
            protein = parcel.readInt(),
            date = parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(price)
        parcel.writeInt(calories)
        parcel.writeInt(totalFat)
        parcel.writeInt(totalCarbohydrate)
        parcel.writeInt(protein)
        parcel.writeString(date)
    }

    override fun describeContents() = 0

    fun getName(): String {
        return name
    }

    fun getPrice(): Float {
        return price
    }

    fun setPrice(newPrice: Float) {
        price = newPrice;
    }
//
//    fun getTimeAdded(): Date {
//        return time
//    }

    fun getCalories(): Int {
        return calories
    }

    fun getTotalFat(): Int {
        return totalFat
    }

    fun getTotalCarbohydrate(): Int {
        return totalCarbohydrate
    }

    fun getProtein(): Int {
        return protein
    }

    fun getProteinPerc(): Float {
        return protein.toFloat() / calories.toFloat() * 100
    }

    fun getCarbsPerc(): Float {
        return totalCarbohydrate.toFloat() / calories.toFloat() * 100
    }

    fun getFatPerc(): Float {
        return totalFat.toFloat() / calories.toFloat() * 100
    }

    fun getDateAdded(): String {
        return date
    }
}

