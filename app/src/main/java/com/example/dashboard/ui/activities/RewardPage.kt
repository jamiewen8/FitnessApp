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

class RewardPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reward_page)


        val sharebtn = findViewById<Button>(R.id.btn_share)
        val sharingImage = findViewById<ImageView>(R.id.waterreward)
        val sharingImage2 = findViewById<ImageView>(R.id.dailystep)

        val steps = intent.getStringExtra("steps")
        val waterProgress = intent.getStringExtra("water")
        sharingImage.alpha = 0.5f
        sharingImage2.alpha = 0.5f

        if(steps?.toInt()!! >= 10){
            sharingImage2.alpha = 1f
        }
        if(waterProgress?.toInt()!! >= 100)
        {
            sharingImage.alpha = 1f
        }
        sharebtn.setOnClickListener {


            /*val bitmap = (sharingImage as BitmapDrawable).bitmap
            val bitmap2 = (sharingImage2 as BitmapDrawable).bitmap
            val bitmap3 = (sharingImage3 as BitmapDrawable).bitmap*/

            /*val file = File(externalCacheDir, "myImage.png")
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut)
            fOut.flush()
            fOut.close()
            file.setReadable(true, false)
            val intent = Intent(Intent.ACTION_SEND)*/

            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.waterreward)
            val intent = Intent(Intent.ACTION_SEND)

            val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", null)

            val uri = Uri.parse(path)

            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.type = "image/png"


            val chooser = Intent.createChooser(intent, "Share using....")
            startActivity(chooser)


        }

    }
}