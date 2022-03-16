package com.example.dashboard

import android.Manifest
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager:SensorManager? = null

    var running = false

    private var totalSteps = 0f

    private var previousTotalSteps = 0f

    private val requestcodeforsteps = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isPermissionGranted()) {
            requestPermission()
        }


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        loadData()
        resetSteps()

        val scanning = findViewById<CardView>(R.id.scan)
        scanning.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val bmi = findViewById<CardView>(R.id.bmipage)
        bmi.setOnClickListener{
            val intent = Intent(this, BMI::class.java)
            startActivity(intent)
        }

        val fooddiary = findViewById<CardView>(R.id.fooddiary)
        fooddiary.setOnClickListener{
            val intent = Intent(this, FoodDiary::class.java)
            startActivity(intent)
        }

    }



    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        // unregister listener
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var steps = findViewById<TextView>(R.id.stepstaken)
        var progresscircle = findViewById<CircularProgressBar>(R.id.progress_pedometer)

        if (running){
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            steps.text = ("$currentSteps")


            progresscircle.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }

        }

    }

    private fun resetSteps(){
        var steps = findViewById<TextView>(R.id.stepstaken)

        steps.setOnClickListener {
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        steps.setOnLongClickListener {
            previousTotalSteps = totalSteps
            steps.text = 0.toString()
            saveData()

            true
        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        Log.d("MainActivity", "$savedNumber")
        previousTotalSteps = savedNumber
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("onAccuracyChanged: Sensor: $sensor; accuracy: $accuracy")
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                requestcodeforsteps
            )
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestcodeforsteps -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                )else {

                    onResume( )
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }
            }
        }
    }
}