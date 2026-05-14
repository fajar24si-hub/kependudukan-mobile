package com.example.fajar_time.Pertemuan_4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fajar_time.Pertemuan_5.FifthActivity
import com.example.fajar_time.Pertemuan_5.WebViewActivity
import com.example.fajar_time.Pertemuan_3.LoginActivity
import com.example.fajar_time.R
import com.example.fajar_time.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        enableEdgeToEdge()
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Setup Bottom Navigation
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    false 
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    false
                }
                else -> false
            }
        }

        // Setup Klik Card Dashboard
        binding.cardWebView.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        binding.cardPotensi.setOnClickListener {
            startActivity(Intent(this, FifthActivity::class.java))
        }

        binding.cardLapor.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("PAGE_TITLE", "Pelaporan Warga")
            intent.putExtra("PARENT_TITLE", "DesaKu")
            intent.putExtra("PARENT_DESC", "Layanan aspirasi dan pengaduan online.")
            startActivity(intent)
        }

        binding.cardLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Keluar Akun")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", false)
                editor.apply()

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
    
    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }
}