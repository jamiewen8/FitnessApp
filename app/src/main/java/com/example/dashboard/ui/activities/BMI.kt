package com.example.dashboard.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboard.R
import org.w3c.dom.Text
import kotlin.math.pow

class BMI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi)
        supportActionBar?.show()

        var man = 0
        var woman = 0

        val imageBoy = findViewById<ImageView>(R.id.maleicon)
        val imageGirl = findViewById<ImageView>(R.id.femaleicon)
        val weight = findViewById<EditText>(R.id.weight_value)
        val height = findViewById<EditText>(R.id.height_value)
        val age = findViewById<EditText>(R.id.age_value)
        val calculateButton = findViewById<Button>(R.id.calculate_button)
        val bmi = findViewById<TextView>(R.id.bmi)
        val calories = findViewById<TextView>(R.id.calories)
        val bmiStatus = findViewById<TextView>(R.id.bmi_status)
        val bmiView = findViewById<LinearLayout>(R.id.bmiView)
        val caloriesView = findViewById<LinearLayout>(R.id.calorieView)
        val calculateAgainButton = findViewById<TextView>(R.id.calculate_again)


        imageBoy.setOnClickListener {
            man = 1
            woman = 0
            imageBoy.setImageResource(R.drawable.male)
            imageBoy.alpha = 1f;
            imageGirl.setImageResource(R.drawable.female)
            imageGirl.alpha = 0.5f;
        }

        imageGirl.setOnClickListener {
            man = 0
            woman = 1
            imageBoy.setImageResource(R.drawable.male)
            imageBoy.alpha = 0.5f;
            imageGirl.setImageResource(R.drawable.female)
            imageGirl.alpha = 1f;
        }

        calculateButton.setOnClickListener {
            var weightValue = 0.0
            var heightValue = 0.0
            var ageValue = 0.0
            if (weight.text.toString().isNotEmpty()) {
                weightValue = weight.text.toString().toDouble()
            }
            if (height.text.toString().isNotEmpty()) {
                heightValue = (height.text.toString().toDouble() / 100)
            }
            if (age.text.toString().isNotEmpty()) {
                ageValue = (age.text.toString().toDouble() / 100)
            }
            if (weightValue > 0.0 && heightValue > 0.0) {
                val bmiValue = String.format("%.2f", weightValue / heightValue.pow(2))
                bmi.text = bmiValue
                bmiStatus.text = bmiStatusValue(weightValue / heightValue.pow(2))
                bmiView.visibility = VISIBLE
                calculateButton.visibility = GONE
            } else
                Toast.makeText(this, "Please Input Weight and Height Values greater than 0", Toast.LENGTH_LONG).show()

            if (ageValue > 0.0 && weightValue > 0.0 && heightValue > 0.0){

                if (man == 1){
                    val caloreintake = String.format("%.2f", 66 + (6.3 * weightValue) + (12.9 *(heightValue/2.54)) - (6.8 * ageValue))
                    calories.text = caloreintake
                    caloriesView.visibility = VISIBLE

                }
                if (woman == 1){
                    val caloreintake = String.format("%.2f", 655 + (4.3 * weightValue) + (4.7 *(heightValue/2.54)) - (4.7 * ageValue))
                    calories.text = caloreintake
                    caloriesView.visibility = VISIBLE
                }
            }else
                Toast.makeText(this, "Please Input Age greater than 0", Toast.LENGTH_SHORT).show()
        }

        calculateAgainButton.setOnClickListener {
            bmiView.visibility = GONE
            calculateButton.visibility = VISIBLE
            weight.text.clear()
            height.text.clear()
            age.text.clear()
            weight.requestFocus()
        }
    }

    private fun bmiStatusValue(bmi: Double): String {
        lateinit var bmiStatus: String
        if (bmi < 18.5)
            bmiStatus = "Underweight"
        else if (bmi >= 18.5 && bmi < 25)
            bmiStatus = "Normal"
        else if (bmi >= 25 && bmi < 30)
            bmiStatus = "Overweight"
        else if (bmi > 30)
            bmiStatus = "Obese"
        return bmiStatus
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_home,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)

        when (item.itemId){
            R.id.action_home -> startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}