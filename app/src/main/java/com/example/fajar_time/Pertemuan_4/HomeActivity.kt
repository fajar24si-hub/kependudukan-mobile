package com.example.fajar_time.Pertemuan_4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fajar_time.Pertemuan_10.TenthActivity
import com.example.fajar_time.Pertemuan_5.FifthActivity
import com.example.fajar_time.Pertemuan_5.WebViewActivity
import com.example.fajar_time.Pertemuan_3.LoginActivity
import com.example.fajar_time.Pertemuan_9.NinthActivity
import com.example.fajar_time.R
import com.example.fajar_time.databinding.ActivityHomeBinding
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

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

        // Klik Card Pertemuan 9
        binding.cardPertemuan9.setOnClickListener {
            startActivity(Intent(this, NinthActivity::class.java))
        }

        binding.cardPertemuan10.setOnClickListener {
            startActivity(Intent(this, TenthActivity::class.java))
        }

        binding.cardLogout.setOnClickListener {
            showLogoutDialog()
        }

        // Setup RecyclerView Berita
        binding.rvBerita.layoutManager = LinearLayoutManager(this)

        // Fetch berita dari API
        fetchBerita()
    }

    private fun fetchBerita() {
        binding.pbBeritaLoading.visibility = View.VISIBLE
        binding.tvBeritaError.visibility = View.GONE
        binding.rvBerita.visibility = View.GONE

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        executor.execute {
            val newsList = mutableListOf<NewsItem>()
            var isError = false

            try {
                // Menggunakan rss2json API dengan feed CNN Indonesia Nasional
                val apiUrl = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.cnnindonesia.com%2Fnasional%2Frss&count=10"
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)
                    val items = json.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val title = item.optString("title", "")
                        val description = item.optString("description", "")
                        val pubDate = item.optString("pubDate", "")
                        val link = item.optString("link", "")

                        // Cari gambar dari 3 sumber: thumbnail → enclosure → img dalam HTML
                        var imageUrl = item.optString("thumbnail", "")

                        // Fallback 1: enclosure (format rss2json)
                        if (imageUrl.isEmpty()) {
                            val enclosure = item.optJSONObject("enclosure")
                            if (enclosure != null) {
                                val encLink = enclosure.optString("link", "")
                                val encType = enclosure.optString("type", "")
                                if (encLink.isNotEmpty() && encType.startsWith("image")) {
                                    imageUrl = encLink
                                }
                            }
                        }

                        // Fallback 2: ekstrak <img src="..."> dari HTML description
                        if (imageUrl.isEmpty()) {
                            val imgRegex = Regex("""<img[^>]+src=["']([^"']+)["']""", RegexOption.IGNORE_CASE)
                            val match = imgRegex.find(description)
                            imageUrl = match?.groupValues?.getOrNull(1) ?: ""
                        }

                        // Bersihkan HTML dari deskripsi
                        val cleanDesc = description.replace(Regex("<[^>]*>"), "").trim()

                        if (title.isNotEmpty()) {
                            newsList.add(NewsItem(title, cleanDesc, pubDate, link, imageUrl))
                        }
                    }
                } else {
                    isError = true
                }
                connection.disconnect()
            } catch (e: Exception) {
                isError = true
            }

            handler.post {
                binding.pbBeritaLoading.visibility = View.GONE
                if (isError || newsList.isEmpty()) {
                    binding.tvBeritaError.visibility = View.VISIBLE
                    binding.rvBerita.visibility = View.GONE
                } else {
                    binding.rvBerita.visibility = View.VISIBLE
                    binding.rvBerita.adapter = NewsAdapter(newsList)
                }
            }
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