package com.eathealthyapp.is3261.eathealthyapp

class Food (
        private var name: String,
        private var price: Number,
//        private var time: Date,
        private var calories: Number,
        private var totalFat: Number,
//        private var saturatedFat: Number,
//        private var transFat: Number,
//        private var cholesterol: Number,
//        private var sodium: Number,
        private var totalCarbohydrate: Number,
//        private var dietaryFiber: Number,
//        private var sugars: Number,
        private var protein: Number
//        private var potassium: Number,
//        private var vitaminA: Number,
//        private var vitaminC: Number,
//        private var calcium: Number,
//        private var iron: Number

) {
    fun getName(): String {
        return name
    }

    fun getPrice(): Number {
        return price
    }

    fun setPrice(newPrice: Float) {
        price = newPrice;
    }
//
//    fun getTime(): Date {
//        return time
//    }

    fun getCalories(): Number {
        return calories
    }

    fun getTotalFat(): Number {
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
    fun getTotalCarbohydrate(): Number {
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
    fun getProtein(): Number {
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
}
