package com.example.snaphealth


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate


class Hydration :  ComponentActivity() {
    private var username: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var gender: String = ""
    private var age: Int = 0
    private var height: Double = 0.0
    private var weight: Double = 0.0
    private var waterMl : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_hydration)

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

        if (age < 4) {
            waterMl = "1000"
        }
        else if (age in 4..8) {
            waterMl = "1200"
        }
        else if (age in 9..13 && gender == "Male") {
            waterMl = "1600"
        }
        else if (age in 9..13 && gender == "Female") {
            waterMl = "1400"
        }
        else if (age in 14..18 && gender == "Male") {
            waterMl = "1900"
        }
        else if (age in 14..18 && gender == "Female") {
            waterMl = "1600"
        }
        else if (age >= 19 && gender == "Male") {
            waterMl = "2600"
        }
        else {
            waterMl = "2100"
        }

        //share preferences (save data so when user reopen the data will not reverse to original)
        //refer https://www.youtube.com/watch?v=rHbE1Xcrg_A for idea
        val sharedPreferences = getSharedPreferences("SP_INFO", MODE_PRIVATE)
        val waterCount = sharedPreferences.getString("WATERCOUNT", waterMl)
        var editor = sharedPreferences.edit()


        //start code
        val smallButton: Button = findViewById(R.id.small_button)
        val largeButton: Button = findViewById(R.id.large_button)
        val fiveButton: Button = findViewById(R.id.five_button)
        val mugButton: Button = findViewById(R.id.mug_button)
        val waterCount_Text: TextView = findViewById(R.id.WaterCount_textView)
        val progressBar:ProgressBar = findViewById(R.id.progressBar)

        waterCount_Text.text = waterCount

        fun updateProgressBar(waterCount: Int) {
            progressBar.progress = waterCount
        }

        smallButton.setOnClickListener{
            val currentWaterCount = Integer.parseInt(waterCount_Text.text as String)
            val newWaterCount = currentWaterCount - 43
            waterCount_Text.text = newWaterCount.toString()
            editor.putString("WATERCOUNT", newWaterCount.toString())
            editor.apply()

            // Update progress bar
            updateProgressBar(newWaterCount)
            if (Integer.parseInt(waterCount_Text.text as String) < 1) {
                Toast.makeText(this, "Congratulations on staying hydrated", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Keep up the great work", Toast.LENGTH_LONG).show()
                waterCount_Text.text = waterMl
            }
        }

        largeButton.setOnClickListener{
            val currentWaterCount = Integer.parseInt(waterCount_Text.text as String)
            val newWaterCount = currentWaterCount - 77
            waterCount_Text.text = newWaterCount.toString()
            editor.putString("WATERCOUNT", newWaterCount.toString())
            editor.apply()

            // Update progress bar
            updateProgressBar(newWaterCount)
            if (Integer.parseInt(waterCount_Text.text as String) < 1) {
                Toast.makeText(this, "Congratulations on staying hydrated", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Keep up the great work", Toast.LENGTH_LONG).show()
                waterCount_Text.text = waterMl
            }
        }

        fiveButton.setOnClickListener{
            val currentWaterCount = Integer.parseInt(waterCount_Text.text as String)
            val newWaterCount = currentWaterCount - 157
            waterCount_Text.text = newWaterCount.toString()
            editor.putString("WATERCOUNT", newWaterCount.toString())
            editor.apply()

            // Update progress bar
            updateProgressBar(newWaterCount)
            if (Integer.parseInt(waterCount_Text.text as String) < 1) {
                Toast.makeText(this, "Congratulations on staying hydrated", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Keep up the great work", Toast.LENGTH_LONG).show()
                waterCount_Text.text = waterMl
            }
        }

        mugButton.setOnClickListener{
            val currentWaterCount = Integer.parseInt(waterCount_Text.text as String)
            val newWaterCount = currentWaterCount - 240
            waterCount_Text.text = newWaterCount.toString()
            editor.putString("WATERCOUNT", newWaterCount.toString())
            editor.apply()

            // Update progress bar
            updateProgressBar(newWaterCount)
            if (Integer.parseInt(waterCount_Text.text as String) < 1) {
                Toast.makeText(this, "Congratulations on staying hydrated", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Keep up the great work", Toast.LENGTH_LONG).show()
                waterCount_Text.text = waterMl
            }
        }



        //time
        val currentDate = LocalDate.now().toString()
        val date_textView: TextView = findViewById(R.id.date_textView)
        var pastTime: String = sharedPreferences.getString("PASSTIME", "2024-01-28").toString()
        if (pastTime != currentDate) {
            editor.putString("PASTTIME", currentDate)
            editor.putString("WATERCOUNT", waterMl)
            editor.commit()
            waterCount_Text.text = waterMl
        }
        date_textView.text = currentDate

        //clear count
        val clearButton: Button = findViewById(R.id.clear_text)
        clearButton.setOnClickListener {
            waterCount_Text.text = waterMl
            editor.putString("WATERCOUNT", waterMl)
            editor.commit()
        }
    }
}
