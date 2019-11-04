package com.eathealthyapp.is3261.eathealthyapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class FragmentFoodListItem : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i("created", "created")

        return inflater.inflate(R.layout.fragment_food_list_item, container, false)
    }

    fun setNutrientInfo(food: Food) {
        val name = view?.findViewById<TextView>(R.id.tvFoodItemName)
        val calories = view?.findViewById<TextView>(R.id.tvFoodItemCalories)
        val protein = view?.findViewById<TextView>(R.id.tvFoodItemProtein)
        val carbs = view?.findViewById<TextView>(R.id.tvFoodItemCarbs)
        val fat = view?.findViewById<TextView>(R.id.tvFoodItemFat)

        Log.i("?", view.toString())


        name?.text = food.getName()
        calories?.text = food.getCalories().toString()
        protein?.text = food.getProtein().toString()
        carbs?.text = food.getTotalCarbohydrate().toString()
        fat?.text = food.getTotalFat().toString()
    }

    fun viewFoodDetailScreen(food: Food) {

    }
}
