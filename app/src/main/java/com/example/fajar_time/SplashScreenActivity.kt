package com.example.fajar_time

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.fajar_time.Message.OnBoarding.OnBoardingActivity
import com.example.fajar_time.Pertemuan_4.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            if (isLogin) {
                // Sudah login langsung ke Home
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // Belum login → selalu tampilkan OnBoarding sebelum Login
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            finish()
        }, 3000)
    }
}