package com.example.fajar_time.Message.OnBoarding

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.fajar_time.Pertemuan_3.LoginActivity
import com.example.fajar_time.R
import com.example.fajar_time.databinding.ActivityOnboardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    // Data konten tiap slide
    private val slides = listOf(
        Triple(
            "Selamat Datang di DesaKu 🏡",
            "Aplikasi layanan administrasi desa yang modern, mudah, dan transparan untuk seluruh warga.",
            android.R.drawable.ic_menu_mapmode
        ),
        Triple(
            "Kelola Data Warga 📋",
            "Akses data kependudukan, laporan warga, dan potensi desa hanya dalam genggaman tangan Anda.",
            android.R.drawable.ic_menu_agenda
        ),
        Triple(
            "Terhubung & Berkembang 🚀",
            "Dapatkan informasi terkini seputar desa, berita, dan layanan publik secara real-time.",
            android.R.drawable.ic_menu_view
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupDots(0)
        setupButtons()
        // Inisialisasi tombol: slide pertama = tampilkan Lewati+Next
        updateButtons(0)
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = slides.size
            override fun createFragment(position: Int): Fragment {
                val (title, desc, icon) = slides[position]
                return OnBoardingFragment.newInstance(title, desc, icon)
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setupDots(position)
                updateButtons(position)
            }
        })
    }

    private fun setupDots(currentPage: Int) {
        binding.dotsIndicator.removeAllViews()

        val activeColor = ContextCompat.getColor(this, R.color.white)
        val inactiveColor = 0x55FFFFFF.toInt()

        for (i in slides.indices) {
            val isActive = i == currentPage

            // Spacer antar dot
            if (i > 0) {
                val spacer = Space(this)
                spacer.layoutParams = LinearLayout.LayoutParams(dpToPx(6), dpToPx(10))
                binding.dotsIndicator.addView(spacer)
            }

            // Buat dot menggunakan GradientDrawable langsung (lebih andal dari XML)
            val dot = View(this)
            val width = if (isActive) dpToPx(28) else dpToPx(10)
            val height = dpToPx(10)
            val params = LinearLayout.LayoutParams(width, height)
            dot.layoutParams = params

            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.RECTANGLE
            drawable.cornerRadius = dpToPx(5).toFloat()
            drawable.setColor(if (isActive) activeColor else inactiveColor)

            dot.background = drawable
            binding.dotsIndicator.addView(dot)
        }
    }

    private fun updateButtons(position: Int) {
        val isLastPage = position == slides.size - 1
        binding.btnAyoMulai.visibility = if (isLastPage) View.VISIBLE else View.GONE
        binding.btnRowContainer.visibility = if (isLastPage) View.GONE else View.VISIBLE
    }

    private fun setupButtons() {
        binding.btnNext.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current < slides.size - 1) {
                binding.viewPager.currentItem = current + 1
            }
        }

        binding.btnLewati.setOnClickListener {
            goToLogin()
        }

        binding.btnAyoMulai.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
