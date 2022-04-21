package com.ajgroup.themoviedbapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        this.window.statusBarColor = ContextCompat.getColor(this,R.color.white)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this@SplashScreen,
                    MainActivity::class.java)
            )
            finish()
        }, 5000)
    }
}