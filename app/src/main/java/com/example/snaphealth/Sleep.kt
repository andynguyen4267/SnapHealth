package com.example.snaphealth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.os.Handler
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.json.JSONArray
import java.io.File
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Sleep : ComponentActivity() {
    private var isNight = false
    private var startTime: Long = 0
    private lateinit var textViewResult: TextView
    private lateinit var textViewSleptIn: TextView
    private var sleptInStarted = false
    private var sleptInStartTime: Long = 0
    private val handler = Handler()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private var username: String = ""
    private var firstname: String = ""
    private var lastname: String = ""
    private var gender: String = ""
    private var age: Int = 0
    private var height: Double = 0.0
    private var weight: Double = 0.0

    private fun fetchWeatherData(city: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val apiKey = "8c34baaea73542a67c5720f6a4796ce7"
                val urlString = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                val jsonResponse = JSONObject(response.toString())
                val main = jsonResponse.getJSONObject("main")
                val temp = main.getDouble("temp")

                withContext(Dispatchers.Main) {
                    updateUI(city, temp)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(city: String, temp: Double) {
        val tempFormatted = String.format("%.1f", temp)
        val sleepAdvice: String = when {
            temp < 16 -> "It's too cold for sleep. Consider warming up the room."
            temp > 24 -> "It's too hot for sleep. Consider cooling down the room."
            else -> "The temperature is perfect for sleep."
        }
        val message = "City: $city\n\nTemp: $tempFormatted°C\n\n$sleepAdvice"
        findViewById<TextView>(R.id.weatherAdviceTextView).text = message
    }

    private val runnable = object : Runnable {
        override fun run() {
            if (isNight) {
                val currentTime = System.currentTimeMillis()
                val displayTime = currentTime - startTime
                val s = displayTime / 1000
                val m = s / 60
                val h = m / 60
//              val result = "$h: ${m % 60}: ${s % 60}"
                val result = String.format("%02d:%02d:%02d", h, m%60, s%60)
                textViewResult.text = result

                if (displayTime >= 10000 && !sleptInStarted) {
                    sleptInStarted = true
                    sleptInStartTime = currentTime
                }

                if (sleptInStarted) {
                    val sleptInTime = currentTime - sleptInStartTime
                    val sleptInS = sleptInTime / 1000
                    val sleptInM = sleptInS / 60
                    val sleptInH = sleptInM / 60
//                  val sleptInResult = "$sleptInH: ${sleptInM % 60} : ${sleptInS % 60}"
                    val sleptInResult = String.format("%02d:%02d:%02d", sleptInH, sleptInM%60, sleptInS%60)
                    textViewSleptIn.text = sleptInResult
                }
                //TODO: Slept in should be based on age (not yet implemented)

                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sleep)
        fetchWeatherData("Irvine")
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

        textViewResult = findViewById(R.id.textView11)
        textViewSleptIn = findViewById(R.id.textView10)
        val switchNight = findViewById<Switch>(R.id.switch1)
        switchNight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startTimer()
            } else {
                stopTimer()
            }
        }
        val analyzeBtn = findViewById<TextView>(R.id.textView7)
        analyzeBtn.setOnClickListener {
            val intent = Intent(this, SleepAnalysis::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }

    private fun startTimer() {
        isNight = true
        startTime = System.currentTimeMillis()
        sleptInStarted = false
        handler.post(runnable)
    }

    private fun stopTimer() {
        if (isNight) {
            handler.removeCallbacks(runnable)
            isNight = false
            saveSleepData()
        }
    }

    private fun saveSleepData() {
        val currentTime = System.currentTimeMillis()
        val sleepTime = currentTime - startTime
        val oversleepTime = if (sleptInStarted) currentTime - sleptInStartTime else 0

        val sleepData = listOf(
            dateFormat.format(Date(startTime)),
            dateFormat.format(Date(currentTime)),
            sleepTime.toString(),
            oversleepTime.toString()
        )

        GlobalScope.launch(Dispatchers.IO) {
            val sleepDataFileName = "sleep_data_$username.csv"
            val file = File(filesDir, sleepDataFileName)
            if (!file.exists()) {
                file.createNewFile()
                file.appendText("Begin Time, End Time, Sleep Time (ms), Oversleep Time (ms)\n")
            }
            file.appendText("${sleepData.joinToString(",")}\n")
        }
    }
}

data class SleepData(
    val sleepTime: Long,
    val oversleepTime: Long,
    val beginTime: String,
    val endTime: String
)
