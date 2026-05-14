package com.example.fajar_time.Pertemuan_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fajar_time.Pertemuan_4.HomeActivity
import com.example.fajar_time.databinding.LoginBinding

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: LoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inisialisasi View Binding (Mencegah NullPointerException)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        enableEdgeToEdge()
        
        // Menggunakan binding.root agar listener terpasang pada layar yang tepat
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            // Simpan sesi login di SharedPreferences
            val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isLogin", true)
            editor.apply()

            Toast.makeText(this, "Selamat Datang di DesaKu", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}