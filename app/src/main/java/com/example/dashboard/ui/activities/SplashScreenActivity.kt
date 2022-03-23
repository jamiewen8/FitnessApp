package com.example.dashboard.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.dashboard.MainActivity
import com.example.dashboard.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide() //removing the action bar above to stop showing the name of the app

        val screen = findViewById<ImageView>(R.id.fitify)
        screen.alpha = 0f
        screen.animate().setDuration(1500).alpha(1f).withEndAction {
            val switch = Intent(this, MainActivity::class.java)
            startActivity(switch)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()

        }

    }
}