package com.example.snaphealth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlin.math.roundToInt

class FoodRecommendation :  ComponentActivity() {
    //Set up empty string for level and goal spinning option
    private var level_value: String = ""
    private var goal_value: String = ""
    private var dietType_value: String = ""
    private var username: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var gender: String = ""
    private var age: Int = 0
    private var height: Double = 0.0
    private var weight: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_food_recommendation)

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

        //Types of level activity, pass to recommendation algorithm
        //chatGPT support me to create spinner
        val level_spinner = findViewById<Spinner>(R.id.spinner)
        val level = arrayOf("Sedentary(little or no exercise)", "Lightly(1-2 days/week)","Moderately(3-5 days/week)", "Very(6-7 days/week)","Extremely(Professional athlete)")
        val level_arrayAdp = ArrayAdapter(this@FoodRecommendation, android.R.layout.simple_spinner_dropdown_item, level)
        level_spinner.adapter = level_arrayAdp

        //get data from user choice
        level_spinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                level_value = level[position]
            }
            } //end

        //Types of goal, pass to recommendation algorithm
        val goal_spinner = findViewById<Spinner>(R.id.spinner2)
        val goal = arrayOf("Lose Weight", "Gain Weight","Maintain Weight")
        val goal_arrayAdp = ArrayAdapter(this@FoodRecommendation, android.R.layout.simple_spinner_dropdown_item, goal)
        goal_spinner.adapter = goal_arrayAdp

        //get data from user choice
        goal_spinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                goal_value = goal[position]
            }
        }


        //Types of diets
        val dietType_spinner = findViewById<Spinner>(R.id.spinner3)
        val dietType = arrayOf("dash", "keto","mediterranean", "paleo", "vegan", "general diet")
        val dietType_arrayAdp = ArrayAdapter(this@FoodRecommendation, android.R.layout.simple_spinner_dropdown_item, dietType)
        dietType_spinner.adapter = dietType_arrayAdp

        dietType_spinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                dietType_value = dietType[position]
            }
        }

        //clickable textView, switch to meal recommendation
        val click_here = findViewById<TextView>(R.id.textView9)
        click_here.setOnClickListener {
            val intent = Intent(this, MealRecommendation::class.java)
            startActivity(intent)
        }


        //Calculate button and display textView to return the result from algorithm
        val calculate = findViewById<Button>(R.id.button)
        val calory_display = findViewById<TextView>(R.id.textView6)

        //pass objects and method from algorithm from recommendation_system
        calculate.setOnClickListener {
            //pass algorithm from FoodRecommendation activity to display calories_intake
            val recommendationSystem = Recommendation_system()
            val bmr = recommendationSystem.BMR_calculate(age, height, weight, gender)
            val tdee = recommendationSystem.tdee(bmr, level_value.toString())
            val caloriesIntake = recommendationSystem.calories_intake(tdee, goal_value.toString()).roundToInt()
            calory_display.text = caloriesIntake.toString()

            click_here.setOnClickListener{
                val intent = Intent(this, MealRecommendation::class.java)
                intent.putExtra("calories", caloriesIntake)
                intent.putExtra("dietType", dietType_value)
                startActivity(intent)
            }
        }
    }
}