package com.eathealthyapp.is3261.eathealthyapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView


class ActivityFoodDetail : AppCompatActivity(), OnFoodParsed {
    val colorProtein = "#B477FD"
    val colorCarbs = "#5CECC9"
    val colorFat = "#FFD15A"
    val colorNone = "#EBEBEB"

    lateinit var foodDBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        foodDBHelper = DBHelper(this)

        val input = findViewById<EditText>(R.id.editTextfoodName)

        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            val query = input.text.toString()
            InternetJSON(this, this, query).execute()
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    override fun OnFoodParsed(food: Food) {

        // temp
        val tv = findViewById<TextView>(R.id.textViewfoodNutrition)
        tv.text = "parse successful -- " +
                food.getName() + " " +
                food.getCalories() + " " +
                food.getTotalFat() + " " +
                food.getProtein() + " " +
                food.getTotalCarbohydrate()

        // temp
        val foodNameTitle = findViewById<TextView>(R.id.tvfoodName)
        foodNameTitle.text = food.getName()

        // set price
        val tvPrice = findViewById<TextView>(R.id.textPrice)
        tvPrice.text = "$${String.format("%.2f", food.getPrice())}"

        drawDonutChart(food)

        // set protein
        // set carbs
        // set fat
    }

    fun drawDonutChart(food: Food) {
        val pieChartView = findViewById<PieChartView>(R.id.foodChart)

        val pieData = ArrayList<SliceValue>()
        pieData.add(SliceValue(food.getProteinPerc(), Color.parseColor(colorProtein)))
        pieData.add(SliceValue(food.getCarbsPerc(), Color.parseColor(colorCarbs)))
        pieData.add(SliceValue(100f - food.getProteinPerc() - food.getCarbsPerc(), Color.parseColor(colorFat)))

        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(false)
        pieChartData.setHasCenterCircle(true)

        pieChartData.centerText1 = food.getCalories().toString()
        pieChartData.centerText1FontSize = 40
        pieChartData.centerText1Color = Color.parseColor("#2A282E")
        pieChartData.centerText1Typeface = Typeface.DEFAULT_BOLD

        pieChartData.centerText2 = "of 1200 kcal"
        pieChartData.centerText2FontSize = 18
        pieChartData.centerText2Color = Color.parseColor("#BDBDBD")
        pieChartData.centerText2Typeface = Typeface.DEFAULT_BOLD

        pieChartData.centerCircleScale = 0.85f

        pieChartView.circleFillRatio = 0.8f
        pieChartView.pieChartData = pieChartData
        pieChartView.isInteractive = false

        // animation
        for (value in pieChartData.values) {
//            value.setTarget(Math.random().toFloat() * 30 + 15)
        }
//        pieChartView.startDataAnimation()
    }

    // TODO: add to db
    fun addFoodToDB(food: FoodRecord) {
        foodDBHelper.insertFood(food)
    }
}
