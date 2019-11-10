package com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.eathealthyapp.is3261.eathealthyapp.DBHelper
import com.eathealthyapp.is3261.eathealthyapp.Food
import com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments.FragmentFoodListItem
import com.eathealthyapp.is3261.eathealthyapp.R
import java.text.SimpleDateFormat
import java.util.*
import com.eathealthyapp.is3261.eathealthyapp.FoodRecord
import com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments.FragmentFoodChart
import com.eathealthyapp.is3261.eathealthyapp.utils.PriceCalculator
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class FragmentHome : Fragment() {
    lateinit var foodDBHelper: DBHelper
    var currentCalenderPage: Calendar = Calendar.getInstance()
    val listOfFoodFragments: ArrayList<Fragment> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_home, container, false)

        foodDBHelper = DBHelper(context!!)
        updateFoodChart()
        populateFoodList()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateTV = view.findViewById<TextView>(R.id.dateTV)
        dateTV.text = getDateString(currentCalenderPage)

        val leftBtn = view.findViewById<ImageButton>(R.id.leftBtn)
        leftBtn.setOnClickListener {
            // Update date
            currentCalenderPage.add(Calendar.DAY_OF_YEAR, -1)
            dateTV.text = getDateString(currentCalenderPage)
            // Update food List
            emptyFoodList()
            populateFoodList()
            // Update food chart
            updateFoodChart()
        }
        val rightBtn = view.findViewById<ImageButton>(R.id.rightBtn)
        rightBtn.setOnClickListener {
            // Update date
            currentCalenderPage.add(Calendar.DAY_OF_YEAR, 1)
            dateTV.text = getDateString(currentCalenderPage)
            // Update food List
            emptyFoodList()
            populateFoodList()
            // Update food chart
            updateFoodChart()
        }

        // TODO: move this part to after payment
        val testbtn = view.findViewById<Button>(R.id.btnTest)
        testbtn.setOnClickListener {
            val food = Food("Coffee",
                    3f,
                    1000,
                    302,
                    13,
                    100,
                    "08 Nov 2019")

            foodDBHelper.insertFood(FoodRecord(food.getName(),
                    food.getPrice(),
                    food.getCalories(),
                    food.getProtein(),
                    food.getTotalCarbohydrate(),
                    food.getTotalFat(),
                    food.getDateAdded())
            )

            emptyFoodList()
            populateFoodList()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i("FragmentHome", "Resumed")
        emptyFoodList()
        populateFoodList()
        updateFoodChart()
    }

    fun populateFoodList() {
        val foods = getAllFoodWithCurrentDate()

        if (foods.isEmpty()) {
            // TODO: set empty image
        } else {
            foods.forEach {
                addFoodToList(it)
            }
        }
    }

    fun addFoodToList(food: Food) {
        val foodListItem = FragmentFoodListItem()

        // add to a list so that it can be removed ltr
        listOfFoodFragments.add(foodListItem)

        val bundle = Bundle()
        bundle.putParcelable("food", food)
        foodListItem.arguments = bundle

        val fragmentManager = activity!!.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.foodListContainer, foodListItem)
        transaction.commit()
    }

    fun emptyFoodList() {
        for (fragment in listOfFoodFragments) {
            activity!!.supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        listOfFoodFragments.clear()
    }

    fun getDateString(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        return dateFormat.format(calendar.time)
    }

    private fun updateFoodChart() {
        val fragmentManager =  childFragmentManager
        val foodChartFragment = fragmentManager.findFragmentById(R.id.foodChartMain) as? FragmentFoodChart
        foodChartFragment?.setTotalNutrientInfo(getAllFoodWithCurrentDate())
    }

    private fun getAllFoodWithCurrentDate(): ArrayList<Food> {
        var foods = ArrayList<Food>()
        var foodRecord = foodDBHelper.readAllFood()

        var name: String
        var price: Float
        var calories: Int
        var protein: Int
        var carbs: Int
        var fat: Int
        var dateAdded: String

        foodRecord.forEach {
            name = it.name
            price = it.price
            calories = it.calories
            protein = it.protein
            carbs = it.carbs
            fat = it.fat
            dateAdded = it.date

            if (dateAdded.equals(getDateString(currentCalenderPage))) {
                val food = Food(name,
                        price,
                        calories,
                        protein,
                        carbs,
                        fat,
                        dateAdded)

                foods.add(food)
            }
        }
        return foods
    }
}
