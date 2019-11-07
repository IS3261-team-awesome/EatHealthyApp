package com.eathealthyapp.is3261.eathealthyapp

class Food (
        private var name: String,
        private var price: Float,
        private var calories: Int,
        private var totalFat: Int,
        private var totalCarbohydrate: Int,
        private var protein: Int,
        private var date: String
) {
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
//
//    fun getSaturatedFat(): Number {
//        return saturatedFat
//    }
//
//    fun getTransFat(): Number {
//        return transFat
//    }
//
//    fun getCholesterol(): Number {
//        return cholesterol
//    }
//
//    fun getSodium(): Number {
//        return sodium
//    }
//
    fun getTotalCarbohydrate(): Int {
        return totalCarbohydrate
    }
//
//    fun getDietaryFiber(): Number {
//        return dietaryFiber
//    }
//
//    fun getSugars(): Number {
//        return sugars
//    }
//
    fun getProtein(): Int {
        return protein
    }
//
//    fun getPotassium(): Number {
//        return potassium
//    }
//
//    fun getVitaminA(): Number {
//        return vitaminA
//    }
//
//    fun getVitaminC(): Number {
//        return vitaminC
//    }
//
//    fun getCalcium(): Number {
//        return calcium
//    }
//
//    fun getIron(): Number {
//        return iron
//    }

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
