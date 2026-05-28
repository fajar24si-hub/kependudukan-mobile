package com.example.fajar_time.Pertemuan_9

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fajar_time.Pertemuan_5.WebViewActivity
import com.example.fajar_time.databinding.ActivityNinthBinding

class NinthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNinthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNinthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Implementasi fitur: Hubungkan setiap card dengan activity terkait
        binding.cardWebDesaku.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        binding.cardPotensiDesa.setOnClickListener {
            Toast.makeText(this, "Fitur Potensi Desa - Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.cardLaporWarga.setOnClickListener {
            Toast.makeText(this, "Fitur Lapor Warga - Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.cardDataPenduduk.setOnClickListener {
            Toast.makeText(this, "Fitur Data Penduduk - Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.cardPengumuman.setOnClickListener {
            Toast.makeText(this, "Fitur Pengumuman - Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }
}
