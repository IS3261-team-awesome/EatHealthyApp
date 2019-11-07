package com.eathealthyapp.is3261.eathealthyapp.fragments.main_fragments


import android.os.Bundle
import android.support.v4.app.Fragment
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
import android.os.Build
import com.eathealthyapp.is3261.eathealthyapp.FoodRecord
import java.time.LocalDate


/**
 * A simple [Fragment] subclass.
 */
class FragmentHome : Fragment() {
    lateinit var foodDBHelper: DBHelper
    lateinit var currentCalendar: Calendar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_home, container, false)
        foodDBHelper = DBHelper(context!!)
        populateFoodList()

        val btn = view.findViewById<Button>(R.id.btnTest)
        btn.setOnClickListener {
            // TODO: add food from qr
            val food = Food("Lemon burger", 3.00f, 1000, 302, 13, 100, 7, 10, 2019)
            foodDBHelper.insertFood(FoodRecord(food.getName(),
                    food.getPrice(),
                    food.getCalories(),
                    food.getProtein(),
                    food.getTotalCarbohydrate(),
                    food.getTotalFat(),
                    food.getDayAdded(),
                    food.getMonthAdded(),
                    food.getYearAdded()))

            // Refresh page
            val ft = fragmentManager!!.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false)
            }
            ft.detach(this).attach(this).commit()
        }

        val dateTV = view.findViewById<TextView>(R.id.dateTV)
        currentCalendar = Calendar.getInstance()
        dateTV.text = getDateString(currentCalendar)

        val leftBtn = view.findViewById<ImageButton>(R.id.leftBtn)
        leftBtn.setOnClickListener {

            currentCalendar.add(Calendar.DAY_OF_YEAR, -1)
            dateTV.text = getDateString(currentCalendar)

            // Update food chart
            updateFoodChart()
        }
        val rightBtn = view.findViewById<ImageButton>(R.id.rightBtn)
        rightBtn.setOnClickListener {
            // Update Date
            currentCalendar.add(Calendar.DAY_OF_YEAR, 1)
            dateTV.text = getDateString(currentCalendar)

            updateFoodChart()
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
        var dayAdded: Int
        var monthAdded: Int
        var yearAdded: Int

        foodRecord.forEach {
            name = it.name
            price = it.price
            calories = it.calories
            protein = it.protein
            carbs = it.carbs
            fat = it.fat
            dayAdded = it.day
            monthAdded = it.month
            yearAdded = it.year

            val date = Date()
            println("--------------------------")
            println(Calendar.getInstance().time.date)
            println(dayAdded)
            println(Calendar.getInstance().time.month)
            println(monthAdded)
            println(2019)
            println(yearAdded)
            if (dayAdded == Calendar.getInstance().time.date &&
                    monthAdded == Calendar.getInstance().time.month &&
                    yearAdded == 2019) {
                // TODO: find a way to get year  added in a proper format
                val food = Food(name,
                        price,
                        calories,
                        protein,
                        carbs,
                        fat,
                        dayAdded,
                        monthAdded,
                        yearAdded)

                addFoodToList(food)
            }
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

    fun getDateString(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        return dateFormat.format(calendar.time)
    }

    fun updateFoodChart() {

    }
}
