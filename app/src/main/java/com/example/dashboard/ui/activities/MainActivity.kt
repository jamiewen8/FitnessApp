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
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dashboard.data.models.Food
import com.example.dashboard.ui.activities.BMI
import com.example.dashboard.ui.activities.FoodDiary
import com.example.dashboard.ui.activities.RewardPage
import com.example.dashboard.ui.activities.WaterIntake
import com.example.dashboard.ui.fragments.foodlist.FoodList
import com.example.dashboard.ui.introscreen.IntroActivity
import com.example.dashboard.ui.viewmodels.FoodViewModel



class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager:SensorManager? = null
    var running = false
    private val viewModel: FoodViewModel by viewModels()
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

    //to see if this is the user's first time using the app if it is then it will show the intro screen
    private var userFirstTime = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() //removing the action bar above to stop showing the name of the app

        loadSPData()
        databaseSetup()
        if (userFirstTime) {
            userFirstTime = false
            saveSPData()

            val i = Intent(this, IntroActivity::class.java)
            startActivity(i)
            finish()
        }

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

    fun databaseSetup(){
        //Add food to database
        val name = arrayListOf<String>("Coco pops", "Frosties","Crunchy nut","Lucky charms")
        val protein = arrayListOf<String>("1.9", "1.6", "2.5", "1.4")
        val fat = arrayListOf<String>("0.6", "0.2", "0.3", "0.5")
        val carbs = arrayListOf<String>("25", "30", "35", "45")
        val drawableSelected = arrayListOf<Int>(1, 2, 3, 4)
        for(i in 0..name.size-1){
            val food = Food(name[i], protein[i], fat[i], carbs[i], drawableSelected[i])
            viewModel.insertFood(food)
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
        previousTotalSteps = savedNumber
    }

    private fun loadSPData(){
        val sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)

        userFirstTime = sharedPreferences.getBoolean("BOOLEAN_FIRST_TIME", true)
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