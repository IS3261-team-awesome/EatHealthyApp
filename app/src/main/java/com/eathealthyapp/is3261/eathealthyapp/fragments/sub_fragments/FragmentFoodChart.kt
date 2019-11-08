package com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eathealthyapp.is3261.eathealthyapp.Food
import com.eathealthyapp.is3261.eathealthyapp.R
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView


class FragmentFoodChart : Fragment() {
    val COLOR_PROTEIN = "#B477FD"
    val COLOR_CARBS = "#5CECC9"
    val COLOR_FAT = "#FFD15A"
    val COLOR_NONE = "#EBEBEB"
    val TOTAL_CALORIES = 1200

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_food_chart, container, false)
    }

    fun setTotalNutrientInfo(foods: ArrayList<Food>) {
        drawDonutChart(foods)
        setNutrientTextViews(foods)
    }

    fun setNutrientInfo(food: Food) {
        drawDonutChart(food)
        setNutrientTextViews(food)
    }

    private fun drawDonutChart(food: Food) {
        val pieChartView = view?.findViewById<PieChartView>(R.id.foodChart)

        val pieData = ArrayList<SliceValue>()

        pieData.add(SliceValue(food.getProteinPerc(), Color.parseColor(COLOR_PROTEIN)))
        pieData.add(SliceValue(food.getCarbsPerc(), Color.parseColor(COLOR_CARBS)))
        pieData.add(SliceValue(food.getFatPerc(), Color.parseColor(COLOR_FAT)))

        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(false)
        pieChartData.setHasCenterCircle(true)

        pieChartData.centerText1 = food.getCalories().toString()
        pieChartData.centerText1FontSize = 40
        pieChartData.centerText1Color = Color.parseColor("#2A282E")
        pieChartData.centerText1Typeface = Typeface.DEFAULT_BOLD

        pieChartData.centerText2 = "kcal"
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

    private fun drawDonutChart(foods: ArrayList<Food>) {
        val pieChartView = view?.findViewById<PieChartView>(R.id.foodChart)

        val totalProtein = getTotalProtein((foods))
        val totalCarbs = getTotalCarbs(foods)
        val totalFat = getTotalFat(foods)

        val pieData = ArrayList<SliceValue>()
        if (totalProtein + totalCarbs + totalFat == 0) {
            pieData.add(SliceValue(1f, Color.parseColor(COLOR_NONE)))
        } else {
            pieData.add(SliceValue(totalProtein.toFloat(), Color.parseColor(COLOR_PROTEIN)))
            pieData.add(SliceValue(totalCarbs.toFloat(), Color.parseColor(COLOR_CARBS)))
            pieData.add(SliceValue(totalFat.toFloat(), Color.parseColor(COLOR_FAT)))
        }

        val pieChartData = PieChartData(pieData)
        pieChartData.setHasLabels(false)
        pieChartData.setHasCenterCircle(true)


        val totalCalories = getTotalProtein(foods) + getTotalProtein(foods) + getTotalFat(foods)
        pieChartData.centerText1 = totalCalories.toString()
        pieChartData.centerText1FontSize = 40
        pieChartData.centerText1Color = Color.parseColor("#2A282E")
        pieChartData.centerText1Typeface = Typeface.DEFAULT_BOLD

        pieChartData.centerText2 = "kcal"
        pieChartData.centerText2FontSize = 20
        pieChartData.centerText2Color = Color.parseColor("#BDBDBD")
        pieChartData.centerText2Typeface = Typeface.DEFAULT_BOLD

        pieChartData.centerCircleScale = 0.85f

        pieChartView?.circleFillRatio = 0.8f
        pieChartView?.pieChartData = pieChartData
        pieChartView?.isInteractive = false

    }

    private fun setNutrientTextViews(food: Food) {
        val tvProteinAmt = view?.findViewById<TextView>(R.id.tvProteinAmt)
        val tvCarbsAmt = view?.findViewById<TextView>(R.id.tvCarbsAmt)
        val tvFatAmt = view?.findViewById<TextView>(R.id.tvFatAmt)

        tvProteinAmt?.text = "${food.getProtein()}g"
        tvCarbsAmt?.text = "${food.getTotalCarbohydrate()}g"
        tvFatAmt?.text = "${food.getTotalFat()}g"
    }

    private fun setNutrientTextViews(foods: ArrayList<Food>) {
        val tvProteinAmt = view?.findViewById<TextView>(R.id.tvProteinAmt)
        val tvCarbsAmt = view?.findViewById<TextView>(R.id.tvCarbsAmt)
        val tvFatAmt = view?.findViewById<TextView>(R.id.tvFatAmt)

        tvProteinAmt?.text = "${getTotalProtein(foods)}g"
        tvCarbsAmt?.text = "${getTotalCarbs(foods)}g"
        tvFatAmt?.text = "${getTotalFat(foods)}g"
    }

    private fun getTotalProtein(foods: ArrayList<Food>): Int {
        var result = 0
        foods.forEach {
            result += it.getProtein()
        }
        return result
    }

    private fun getTotalCarbs(foods: ArrayList<Food>): Int {
        var result = 0
        foods.forEach {
            result += it.getTotalCarbohydrate()
        }
        return result
    }

    private fun getTotalFat(foods: ArrayList<Food>): Int {
        var result = 0
        foods.forEach {
            result += it.getTotalFat()
        }
        return result
    }
}
