package com.example.dashboard

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager:SensorManager? = null
    var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private val requestcodeforsteps = 100
    //the above are for the pedometer. Calculating the total steps, previous steps and getting the request code for the pedometer to work
    // making sure that running is off and that the sensor is checked
    //api >29 must include the permissions

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    //water intake animations
    private var water_intake :WaterIntake? = null
    private var seek_bar : SeekBar? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWaterIntake()

        if (isPermissionGranted()) {
            requestPermission()
        }


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        loadData()
        resetSteps()

        //load data and reset steps for the pedometer


        createNotificationChannel()
        //notification for the pedometer







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

            if (currentSteps == 10){
                sendNotification()
                //sends the notification when it hits a certain amount of steps
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

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //make sure to send notification for devices above oreo
            val name = "Notification title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply{
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        //create an intent to open activity

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //bitmap converter

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.well_done)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.running)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("You Hit Your Goal!!")
            .setContentText("For reaching 8000 steps")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)



        with(NotificationManagerCompat.from(this,)){
            notify(notificationId, builder.build())
        }
    }


    private fun initWaterIntake() {
        water_intake = findViewById(R.id.water_animation)
        seek_bar = findViewById(R.id.seek_bar)
        seek_bar!!.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                water_intake!!.setProgress(progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }



}