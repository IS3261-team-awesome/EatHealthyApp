package com.eathealthyapp.is3261.eathealthyapp

class Food (
        private var m_name: String,
        private var m_calories: String
) {
    fun getName(): String {
        return m_name
    }

    fun getCalories(): String {
        return m_calories
    }
}