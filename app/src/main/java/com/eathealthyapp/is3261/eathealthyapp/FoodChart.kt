package com.eathealthyapp.is3261.eathealthyapp


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView


class FoodChart : Fragment() {
    val colorProtein = "#B477FD"
    val colorCarbs = "#5CECC9"
    val colorFat = "#FFD15A"
    val colorNone = "#EBEBEB"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food_chart, container, false)
    }

    fun drawDonutChart(food: Food) {
        Log.i("draw chart for food: ", food.getName())
        val pieChartView = view?.findViewById<PieChartView>(R.id.foodChart)

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

        pieChartView?.circleFillRatio = 0.8f
        pieChartView?.pieChartData = pieChartData
        pieChartView?.isInteractive = false

        // animation
//        for (value in pieChartData.values) {
//            value.setTarget(Math.random().toFloat() * 30 + 15)
//        }
//        pieChartView.startDataAnimation()
    }
}
