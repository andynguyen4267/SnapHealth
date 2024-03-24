package com.example.snaphealth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button


class MainActivity :  ComponentActivity() {
    private var username: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var gender: String = ""
    private var age: Int = 0
    private var height: Double = 0.0
    private var weight: Double = 0.0
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = intent.extras
        if (data != null) {
            username = data.getString("username").toString()
            firstname = data.getString("firstname").toString()
            lastname = data.getString("lastname").toString()
            gender = data.getString("gender").toString()
            age = data.getInt("age")
            height = data.getDouble("height")
            weight = data.getDouble("weight")
        }
        println(username)
        println(firstname)
        println(lastname)
        println(gender)
        println(age)
        println(height)
        println(weight)
        //Switch to Hydration Reminder Feature
        val switchButton = findViewById<Button>(R.id.btnWater)
        switchButton.setOnClickListener {
            val intent = Intent(this, Hydration::class.java)
            intent.putExtra("username", username)
            intent.putExtra("firstname", firstname)
            intent.putExtra("lastname", lastname)
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }

        //Switch to Food Recommendation Feature
        val switchButton1 = findViewById<Button>(R.id.btnFood)
        switchButton1.setOnClickListener {
            val intent = Intent(this, FoodRecommendation::class.java)
            intent.putExtra("username", username)
            intent.putExtra("firstname", firstname)
            intent.putExtra("lastname", lastname)
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }

        //Switch to Sleep Track Feature
        val switchButton2 = findViewById<Button>(R.id.btnSleep)
        switchButton2.setOnClickListener {
            val intent = Intent(this, Sleep::class.java)
            intent.putExtra("username", username)
            intent.putExtra("firstname", firstname)
            intent.putExtra("lastname", lastname)
            intent.putExtra("gender", gender)
            intent.putExtra("age", age)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }
    }
}

