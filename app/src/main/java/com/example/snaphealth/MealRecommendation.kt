package com.example.snaphealth


import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.roundToInt

class MealRecommendation : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_meal_recommendation)

        //read Diets.csv files
        val csvfile = InputStreamReader(assets.open("Diets.csv"))
        val reader = BufferedReader(csvfile)
        var line : String?
        val meals: MutableList<String> = mutableListOf()

        while (reader.readLine().also { line = it } != null) {
            val row: List<String> = line!!.split(",")
            if (row.size >= 6) {
                meals.add(line!!)
            }
        }

        val targetCalories = intent.getIntExtra("calories", 0)
        //set up a bound +- 100 for acceptable result
        val minCalories = targetCalories - 100
        val maxCalories = targetCalories + 100
        meals.shuffle()

        val selectedMeals: MutableList<String> = mutableListOf()
        var totalSelectedCalories = 0.0
        val selectedDietType = intent.getStringExtra("dietType")

        for (meal in meals) {
            if (selectedMeals.size == 3) {
                break
            }

            //filter dataset based on Diet type
            //chatGPT assist me to filtering csv file
            val row: List<String> = meal.split(",")
            val dietType = row[0]
            if (selectedDietType == "general diet" || selectedDietType == dietType) {
                //convert to Double if it has or null if fail to convert
                val protein = row[3].toDoubleOrNull() ?: continue
                val carbs = row[4].toDoubleOrNull() ?: continue
                val fat = row[5].toDoubleOrNull() ?: continue
                // 1pro -> 1 cal, 1card = 1fat -> 4 cal
                val totalCalories = (protein * 1 + carbs * 4 + fat * 4)

                //meals consider acceptable if its calories is in bound
                if (totalSelectedCalories + totalCalories <= maxCalories && totalSelectedCalories + totalCalories >= minCalories) {
                    selectedMeals.add(meal)
                    totalSelectedCalories += totalCalories
                }
            }
            //end
        }
        val linearContainer = findViewById<LinearLayout>(R.id.linearContainer)

        for (meal in selectedMeals) {
            val row: List<String> = meal.split(",")
            val protein = row[3].toDoubleOrNull() ?: continue
            val carbs = row[4].toDoubleOrNull() ?: continue
            val fat = row[5].toDoubleOrNull() ?: continue
            val totalCalories = (protein + carbs * 4 + fat * 4).roundToInt()

            //UI stuff -> create box contain each meals
            val cardView = CardView(this)
            cardView.setBackgroundResource(R.drawable.button_background)
            val layoutParams = LinearLayout.LayoutParams(
                resources.getDimension(R.dimen.card_size).toInt(),
                resources.getDimension(R.dimen.card_size).toInt()
            )
            layoutParams.gravity = Gravity.CENTER
            layoutParams.setMargins(16, 16, 16, 16)
            cardView.layoutParams = layoutParams
            cardView.radius = 16f

            val mealTextView = TextView(this)
            mealTextView.text = "${row[1]}  \n\nTotal Calories: $totalCalories \n\n\n\nClick here for Macros information"
            mealTextView.textSize = 18f
            mealTextView.setPadding(16, 16, 16, 16)
            mealTextView.setTextColor(android.graphics.Color.WHITE)
            mealTextView.setOnClickListener { view ->
                showExtraInformation(meal)
            }
            cardView.addView(mealTextView)
            linearContainer.addView(cardView)
            //end UI stuff
        }
    }
//Show extra information of the meals when user click on the meal box
    private fun showExtraInformation(meal: String) {
        val row: List<String> = meal.split(",")
        val recipeName = row[1]
        val cuisineType = row[2]
        val dietType = row[0]
        val protein = row[3]
        val carbs = row[4]
        val fat = row[5]

        val message =
            "This delicious dish, A well-known $cuisineType cuisine,  " +
                    "usually called $recipeName, " +
                    "Perfect for those following a $dietType diet. " +
                    "It provide a balanced nutrition. " +
                    "Packed with $protein grams of protein, " +
                    "which is good for your muscles and be a saver of your hunger. " +
                    "The carbohydrates, totaling $carbs grams, provide a rich energy " +
                    "while the $fat grams of healthy fats contribute to the vitamin absorption ability. " +
                    "NOTE! CAREFULLY RESEARCH THE INGREDIENTS BEFORE EATING TO AVOID ALLERGIC SIDE EFFECTS"

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Extra Information")
        builder.setMessage(message)
        builder.setPositiveButton("Close") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
