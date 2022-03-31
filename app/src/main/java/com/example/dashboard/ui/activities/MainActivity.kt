package com.example.dashboard.ui.activities

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
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.dashboard.R
import com.example.dashboard.ui.introscreen.IntroActivity


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
    private var water_intake : WaterIntake? = null
    private var seek_bar : SeekBar? = null
    private var waterProgress = 0

    //to see if this is the user's first time using the app if it is then it will show the intro screen
    private var userFirstTime = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() //removing the action bar above to stop showing the name of the app

        loadSPData()





        //setupActionBarWithNavController(findNavController(R.id.navHostFragment))






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


        startScreen()



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
        val calendar = findViewById<TextView>(R.id.MyDashboard)
        calendar.setOnClickListener{
            val intent = Intent(this, CalendarView::class.java)
            startActivity(intent)
        }




        /*val fooddiary = findViewById<CardView>(R.id.fooddiary)
        fooddiary.setOnClickListener{
            setupActionBarWithNavController(findNavController(R.id.navHostFragment))
        }*/
        /*fooddiary.setOnClickListener{

            val myFragment = FoodList()
            val fragment : Fragment? =

                supportFragmentManager.findFragmentByTag(FoodList::class.java.simpleName)

            if(fragment !is FoodList){
                supportFragmentManager.beginTransaction()
                    .add(R.id.fooddiary, myFragment, FoodList::class.java.simpleName)
                    .commit()

            }*/


        val rewardspage = findViewById<CardView>(R.id.rewardspage)
        rewardspage.setOnClickListener{
            val intent = Intent(this, RewardPage::class.java)
            var currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            intent.putExtra("steps", currentSteps.toString())
            intent.putExtra("water", waterProgress.toString())
            startActivity(intent)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun startScreen(){
        if (userFirstTime) {
            userFirstTime = false
            saveSPData()


            val i = Intent(this, IntroActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var steps = findViewById<TextView>(R.id.stepstaken)
        var progresscircle = findViewById<CircularProgressBar>(R.id.progress_pedometer)

        if (running){

            totalSteps = event!!.values[0]
            var currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            if (userFirstTime) {
                totalSteps = 0f
                steps.text = ("$currentSteps")
                progresscircle.apply {
                    setProgressWithAnimation(currentSteps.toFloat())
                }
            }
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
    private fun saveSPData(){
        val sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean("BOOLEAN_FIRST_TIME", userFirstTime)
            apply()
        }
    }


    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        Log.d("MainActivity", "$savedNumber")


        previousTotalSteps = 0f
    }

    private fun loadSPData(){
        val sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)

        userFirstTime = sharedPreferences.getBoolean("BOOLEAN_FIRST_TIME", true)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("onAccuracyChanged: Sensor: $sensor; accuracy: $accuracy")
    }

    private fun requestPermission() {
        var steps = findViewById<TextView>(R.id.stepstaken)
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

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources,
            R.drawable.well_done
        )
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources,
            R.drawable.running
        )


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
        var waterButton = findViewById(R.id.waterAdd) as Button

        waterButton.setOnClickListener {
            waterProgress += 5
            Toast.makeText(this, "Added 100ml", Toast.LENGTH_SHORT).show()
            water_intake!!.setProgress(waterProgress.toFloat())
        }

        waterButton.setOnLongClickListener {
            waterProgress = 0
            water_intake!!.setProgress(waterProgress.toFloat())
            true
        }

    }

    //this is to make sure that when the user swaps the layout to portrait to horizontal or back the water progress will not change

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val waterNumber = waterProgress
        outState.putInt("savedWaterProgress", waterProgress)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val waterNumberInt = savedInstanceState.getInt("savedWaterProgress",0)
        waterProgress = waterNumberInt
        water_intake!!.setProgress(waterProgress.toFloat())


    }

}