package com.example.snaphealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray

import org.json.JSONObject
import java.io.File

class SignupPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val firstNameEditText = findViewById<EditText>(R.id.firstName)
        val lastNameEditText = findViewById<EditText>(R.id.lastName)
        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val genderGroup = findViewById<RadioGroup>(R.id.genderGroup)
        val ageEditText = findViewById<EditText>(R.id.age)
        val heightEditText = findViewById<EditText>(R.id.height)
        val weightEditText = findViewById<EditText>(R.id.weight)
        val submitButton = findViewById<Button>(R.id.submit)
        submitButton.setOnClickListener {
            if (validateForm(firstNameEditText, lastNameEditText, usernameEditText, passwordEditText, genderGroup, ageEditText, heightEditText, weightEditText)) {
                val firstname = firstNameEditText.text.toString()
                val lastname = lastNameEditText.text.toString()
                val password = passwordEditText.text.toString()
                val username = usernameEditText.text.toString()
                val gender = findViewById<RadioButton>(genderGroup.checkedRadioButtonId).text.toString()
                val age = ageEditText.text.toString().toInt()
                val height = heightEditText.text.toString().toDouble()
                val weight = weightEditText.text.toString().toDouble()
                val infoArray = arrayOf(firstname, lastname, password, gender, age, height, weight)
                val infoJsonArray = JSONArray(infoArray)
                val file = File(filesDir, "user_data.json")
                val existingJsonString = if (file.exists()) {
                    file.readText()
                }
                else {
                    "{}"
                }
                val existingJsonObject = JSONObject(existingJsonString)
                if (existingJsonObject.has(username)) {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                }
                else {
                    existingJsonObject.put(username, infoJsonArray)
                    file.writeText(existingJsonObject.toString())
                    finish()
                }
            }
        }
    }

    private fun validateForm(vararg fields: Any): Boolean {
        var isValid = true
        for (field in fields) {
            when (field) {
                is EditText -> {
                    if (field.text.toString().trim().isEmpty()) {
                        field.error = "This field is required"
                        isValid = false
                    }
                }
                is RadioGroup -> {
                    if (field.checkedRadioButtonId == -1) {
                        // Assuming you have a default error message TextView for the RadioGroup
                        val errorMessage = findViewById<TextView>(R.id.gender_error_message)
                        errorMessage.text = "Please select a gender"
                        isValid = false
                    }
                }
            }
        }
        return isValid
    }
}

