package com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eathealthyapp.is3261.eathealthyapp.Food
import com.eathealthyapp.is3261.eathealthyapp.R
import com.eathealthyapp.is3261.eathealthyapp.activities.ActivityFoodDetail


class FragmentFoodListItem : Fragment() {
    var food: Food? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food_list_item, container, false)

        food = arguments?.getParcelable("food")

        view.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("food", food)
            val intent = Intent(context, ActivityFoodDetail::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNutrientInfo(food)
    }

    private fun setNutrientInfo(food: Food?) {
        val tvName = view?.findViewById<TextView>(R.id.tvFoodItemName)
        val tvCalories = view?.findViewById<TextView>(R.id.tvFoodItemCalories)
        val tvProtein = view?.findViewById<TextView>(R.id.tvFoodItemProtein)
        val tvCarbs = view?.findViewById<TextView>(R.id.tvFoodItemCarbs)
        val tvFat = view?.findViewById<TextView>(R.id.tvFoodItemFat)

        tvName?.text = food?.getName()
        tvCalories?.text = food?.getCalories().toString()
        tvProtein?.text = food?.getProtein().toString()
        tvCarbs?.text = food?.getTotalCarbohydrate().toString()
        tvFat?.text = food?.getTotalFat().toString()
    }
}
