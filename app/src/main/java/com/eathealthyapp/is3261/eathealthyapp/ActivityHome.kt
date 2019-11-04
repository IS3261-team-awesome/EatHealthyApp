package com.eathealthyapp.is3261.eathealthyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout

class ActivityHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val foodListContainer = findViewById<LinearLayout>(R.id.foodListContainer)
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val foodListItem = FragmentFoodListItem()
        transaction.add(R.id.foodListContainer, foodListItem)

        transaction.commit()
    }
}
