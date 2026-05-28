package com.example.fajar_time.Pertemuan_9

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fajar_time.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // List data pengaturan yang diperbanyak
        val settingsItems = listOf(
            SettingsItem(
                "Profil Pengguna",
                "Kelola informasi profil, foto, dan detail akun",
                android.R.drawable.ic_menu_myplaces
            ),
            SettingsItem(
                "Keamanan Akun",
                "Ubah kata sandi dan atur keamanan dua langkah",
                android.R.drawable.ic_lock_lock
            ),
            SettingsItem(
                "Notifikasi",
                "Atur preferensi pemberitahuan aplikasi",
                android.R.drawable.ic_popup_reminder
            ),
            SettingsItem(
                "Bahasa",
                "Pilih bahasa aplikasi yang diinginkan (ID/EN)",
                android.R.drawable.ic_menu_search
            ),
            SettingsItem(
                "Tema & Tampilan",
                "Pilih mode terang, gelap, atau otomatis",
                android.R.drawable.ic_menu_manage
            ),
            SettingsItem(
                "Penyimpanan & Data",
                "Kelola penggunaan memori dan hapus cache",
                android.R.drawable.ic_menu_save
            ),
            SettingsItem(
                "Kebijakan Privasi",
                "Kebijakan privasi dan perlindungan data pengguna",
                android.R.drawable.ic_menu_info_details
            ),
            SettingsItem(
                "Syarat & Ketentuan",
                "Ketentuan layanan penggunaan aplikasi DesaKu",
                android.R.drawable.ic_menu_view
            ),
            SettingsItem(
                "Pusat Bantuan",
                "Pertanyaan umum dan panduan aplikasi",
                android.R.drawable.ic_menu_help
            ),
            SettingsItem(
                "Hubungi Kami",
                "Hubungi tim support jika ada kendala",
                android.R.drawable.ic_dialog_email
            ),
            SettingsItem(
                "Tentang Aplikasi",
                "Informasi versi, lisensi, dan pengembang",
                android.R.drawable.ic_dialog_info
            ),
            SettingsItem(
                "Hapus Cache",
                "Bersihkan data sementara aplikasi",
                android.R.drawable.ic_menu_delete
            ),
            SettingsItem(
                "Keluar",
                "Keluar dari sesi akun Anda saat ini",
                android.R.drawable.ic_lock_power_off
            )
        )

        val adapter = SettingsAdapter(this, settingsItems)
        binding.listViewSettings.adapter = adapter

        // Set click listener untuk item ListView
        binding.listViewSettings.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = settingsItems[position]
            Toast.makeText(
                this,
                "Membuka: ${selectedItem.title}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
