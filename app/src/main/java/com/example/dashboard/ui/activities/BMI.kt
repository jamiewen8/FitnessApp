package com.example.dashboard.ui.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboard.R
import kotlin.math.pow

class BMI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi)
        supportActionBar?.hide()
        val imageBoy = findViewById<ImageView>(R.id.maleicon)
        val imageGirl = findViewById<ImageView>(R.id.femaleicon)
        val weight = findViewById<EditText>(R.id.weight_value)
        val height = findViewById<EditText>(R.id.height_value)
        val calculateButton = findViewById<Button>(R.id.calculate_button)
        val bmi = findViewById<TextView>(R.id.bmi)
        val bmiStatus = findViewById<TextView>(R.id.bmi_status)
        val bmiView = findViewById<LinearLayout>(R.id.bmiView)
        val calculateAgainButton = findViewById<TextView>(R.id.calculate_again)

        imageBoy.setOnClickListener {
            imageBoy.setImageResource(R.drawable.male)
            imageBoy.alpha = 1f;
            imageGirl.setImageResource(R.drawable.female)
            imageGirl.alpha = 0.5f;
        }

        imageGirl.setOnClickListener {
            imageBoy.setImageResource(R.drawable.male)
            imageBoy.alpha = 0.5f;
            imageGirl.setImageResource(R.drawable.female)
            imageGirl.alpha = 1f;
        }

        calculateButton.setOnClickListener {
            var weightValue = 0.0
            var heightValue = 0.0
            if (weight.text.toString().isNotEmpty()) {
                weightValue = weight.text.toString().toDouble()
            }
            if (height.text.toString().isNotEmpty()) {
                heightValue = (height.text.toString().toDouble() / 100)
            }
            if (weightValue > 0.0 && heightValue > 0.0) {
                val bmiValue = String.format("%.2f", weightValue / heightValue.pow(2))
                bmi.text = bmiValue
                bmiStatus.text = bmiStatusValue(weightValue / heightValue.pow(2))
                bmiView.visibility = VISIBLE
                calculateButton.visibility = GONE
            } else
                Toast.makeText(this, "Please Input Weight and Height Values greater than 0", Toast.LENGTH_LONG).show()
        }

        calculateAgainButton.setOnClickListener {
            bmiView.visibility = GONE
            calculateButton.visibility = VISIBLE
            weight.text.clear()
            height.text.clear()
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
}