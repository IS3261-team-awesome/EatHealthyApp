package com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.eathealthyapp.is3261.eathealthyapp.DBHelper
import com.eathealthyapp.is3261.eathealthyapp.Food
import com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments.FragmentFoodListItem
import com.eathealthyapp.is3261.eathealthyapp.R


/**
 * A simple [Fragment] subclass.
 */
class FragmentHome : Fragment() {
    lateinit var foodDBHelper: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_home, container, false)
        foodDBHelper = DBHelper(context!!)
        populateFoodList()

        val btn = view.findViewById<Button>(R.id.btnTest)
        btn.setOnClickListener {
            // TODO: add food from qr
            val food = Food("Lemon Juice", 3.00f, 1000, 302, 13, 100)
            addFoodToList(food)
        }

        return view
    }

    fun populateFoodList() {
        var foodRecord = foodDBHelper.readAllFood()

        var name: String
        var price: Float
        var calories: Int
        var protein: Int
        var carbs: Int
        var fat: Int

        foodRecord.forEach {
            name = it.name
            price = it.price
            calories = it.calories
            protein = it.protein
            carbs = it.carbs
            fat = it.fat


            // date = it.date
            // if (date is selectedDate)
            //     addFoodToList (food)

            val food = Food(name, price, calories, protein, carbs, fat)
            addFoodToList(food)
        }
    }

    fun addFoodToList(food: Food) {
        val foodListItem = FragmentFoodListItem()

        val bundle = Bundle()
        bundle.putString("name", food.getName())
        bundle.putFloat("price", food.getPrice())
        bundle.putInt("calories", food.getCalories())
        bundle.putInt("protein", food.getProtein())
        bundle.putInt("carbs", food.getTotalCarbohydrate())
        bundle.putInt("fat", food.getTotalFat())

        foodListItem.setArguments(bundle)

        val fragmentManager = activity!!.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.foodListContainer, foodListItem)
        transaction.commit()
    }
}
