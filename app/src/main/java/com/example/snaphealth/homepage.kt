package com.example.snaphealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import android.widget.Toast
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import android.widget.EditText
import android.widget.RadioButton
import org.json.JSONObject
import java.io.File

class homepage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val usernameEditText = findViewById<EditText>(R.id.username_input)
        val passwordEditText = findViewById<EditText>(R.id.password_input)
        val loginBtn = findViewById<Button>(R.id.login_btn)
        val signupBtn = findViewById<Button>(R.id.signup_btn)

        signupBtn.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val inputUsername = usernameEditText.text.toString()
            val inputPassword = passwordEditText.text.toString()

//            val user = readUserData()
//            if (user != null && inputUsername == user.username && inputPassword == user.password) {
//                // Login successful
//                val intent = Intent(this, MainActivity::class.java) // Change MainActivity to the next activity after login
//                startActivity(intent)
//                finish()
//            } else {
//                // Login failed
//                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
//            }
            val file = File(filesDir, "user_data.json")
            val dataJsonString = if (file.exists()) {
                file.readText()
            }
            else {
                "{}"
            }
            val dataJsonObject = JSONObject(dataJsonString)
            if (dataJsonString == "{}") {
                Toast.makeText(this, "Please sign up", Toast.LENGTH_SHORT).show()
                file.writeText(dataJsonObject.toString())
            }
            else {
                if (dataJsonObject.has(inputUsername)) {
                    val dataJsonArray = dataJsonObject.getJSONArray(inputUsername)
                    if (inputPassword == dataJsonArray.get(2)) {
                        val firstname = dataJsonArray.getString(0)
                        val lastname = dataJsonArray.getString(1)
                        val gender = dataJsonArray.getString(3)
                        val age = dataJsonArray.getInt(4)
                        val height = dataJsonArray.getDouble(5)
                        val weight = dataJsonArray.getDouble(6)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("username", inputUsername)
                        intent.putExtra("firstname", firstname)
                        intent.putExtra("lastname", lastname)
                        intent.putExtra("gender", gender)
                        intent.putExtra("age", age)
                        intent.putExtra("height", height)
                        intent.putExtra("weight", weight)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    private fun readUserData(): User? {
//        val filename = "user_data.json"
//        return try {
//            val fileInputStream = openFileInput(filename)
//            val inputStreamReader = InputStreamReader(fileInputStream)
//            val bufferedReader = BufferedReader(inputStreamReader)
//            val stringBuilder = StringBuilder()
//            var text: String?
//            while (run { text = bufferedReader.readLine(); text } != null) {
//                stringBuilder.append(text)
//            }
//            val gson = Gson()
//            gson.fromJson(stringBuilder.toString(), User::class.java)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
}
