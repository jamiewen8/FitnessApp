package com.example.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scanning = findViewById<androidx.cardview.widget.CardView>(R.id.scan)
        scanning.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val bmi = findViewById<androidx.cardview.widget.CardView>(R.id.bmipage)
        bmi.setOnClickListener{
            val intent = Intent(this, BMI::class.java)
            startActivity(intent)
        }

        val fooddiary = findViewById<androidx.cardview.widget.CardView>(R.id.fooddiary)
        fooddiary.setOnClickListener{
            val intent = Intent(this, FoodDiary::class.java)
            startActivity(intent)
        }

    }
}