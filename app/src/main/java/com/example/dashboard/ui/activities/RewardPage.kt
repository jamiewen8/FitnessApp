package com.example.dashboard.ui.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import com.example.dashboard.R
import com.example.dashboard.ui.activities.MainActivity

import kotlinx.android.synthetic.main.activity_main.*

class RewardPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reward_page)



        val sharebtn = findViewById<Button>(R.id.btn_share)
        val sharingImage = findViewById<ImageView>(R.id.waterreward)
        val sharingImage2 = findViewById<ImageView>(R.id.dailystep)
        //var steps : MainActivity? = null




        sharebtn.setOnClickListener {


            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.waterreward)
            val bitmap2 = BitmapFactory.decodeResource(resources, R.drawable.dailystep)
            val intent = Intent(Intent.ACTION_SEND)
            val intent2 = Intent(Intent.ACTION_SEND)

            val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)
            val path2 = MediaStore.Images.Media.insertImage(contentResolver, bitmap2, "Title", null)

            val uri = Uri.parse(path)
            val uri2 = Uri.parse(path2)



            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent2.putExtra(Intent.EXTRA_STREAM, uri2)


            intent.type = "image/png"
            intent2.type = "image/png"


            val chooser = Intent.createChooser(intent, "Share using....")
            val chooser2 = Intent.createChooser(intent2, "Share using....")

            startActivity(chooser)
            startActivity(chooser2)





        }

    }
}