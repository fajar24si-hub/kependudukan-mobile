package com.example.fajar_time.Pertemuan_2

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.fajar_time.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.TextView

class PerhitunganActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PerhitunganActivity"
    }

    // Deklarasi View Components
    private lateinit var radioGroup: RadioGroup
    private lateinit var input1: TextInputEditText  // Panjang
    private lateinit var input2: TextInputEditText  // Lebar
    private lateinit var input3: TextInputEditText  // Tinggi (khusus balok)
    private lateinit var input1Layout: TextInputLayout
    private lateinit var input2Layout: TextInputLayout
    private lateinit var input3Layout: TextInputLayout
    private lateinit var btnHitung: MaterialButton
    private lateinit var tvFormula: TextView
    private lateinit var tvHasil: TextView
    private lateinit var tvSatuan: TextView

    // Class perhitungan
    private lateinit var perhitungan: Perhitungan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perhitungan)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Perhitungan"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        Log.d(TAG, "onCreate: Activity Perhitungan dimulai")

        // Inisialisasi class perhitungan
        perhitungan = Perhitungan()

        // Inisialisasi view
        initViews()

        // Setup listeners
        setupListeners()

        // Setup default state (Persegi Panjang)
        updateInputFieldsForPersegiPanjang()
    }

    private fun initViews() {
        radioGroup = findViewById(R.id.radioGroup)
        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input1Layout = findViewById(R.id.input1Layout)
        input2Layout = findViewById(R.id.input2Layout)
        input3Layout = findViewById(R.id.input3Layout)
        btnHitung = findViewById(R.id.btnHitung)
        tvFormula = findViewById(R.id.tvFormula)
        tvHasil = findViewById(R.id.tvHasil)
        tvSatuan = findViewById(R.id.tvSatuan)

        Log.d(TAG, "initViews: Semua view berhasil diinisialisasi")
    }

    private fun setupListeners() {
        // RadioGroup listener untuk切换 jenis bangun
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Log.d(TAG, "RadioGroup changed: checkedId = $checkedId")
            when (checkedId) {
                R.id.radioPersegiPanjang -> {
                    Log.i(TAG, "Memilih: Persegi Panjang")
                    updateInputFieldsForPersegiPanjang()
                }
                R.id.radioBalok -> {
                    Log.i(TAG, "Memilih: Balok")
                    updateInputFieldsForBalok()
                }
            }
            // Reset hasil saat berganti pilihan
            resetResult()
        }

        // Button hitung listener
        btnHitung.setOnClickListener {
            Log.i(TAG, "Tombol Hitung ditekan")
            performCalculation()
        }

        // Klik lama pada TV Hasil akan memicu error fatal yang menghentikan aplikasi
        tvHasil.setOnLongClickListener {
            Log.e(TAG, "FATAL: Menjalankan simulasi system crash!")
            throw RuntimeException("SYSTEM FAILURE SIMULATION: Aplikasi dipaksa berhenti!")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun updateInputFieldsForPersegiPanjang() {
        Log.d(TAG, "updateInputFieldsForPersegiPanjang: Mode Persegi Panjang")

        input1Layout.hint = "Panjang (cm)"
        input2Layout.hint = "Lebar (cm)"
        input3Layout.visibility = android.view.View.GONE

        // Update satuan
        tvSatuan.text = "cm²"

        // Kosongkan input3
        input3.setText("")
    }

    private fun updateInputFieldsForBalok() {
        Log.d(TAG, "updateInputFieldsForBalok: Mode Balok")

        input1Layout.hint = "Panjang (cm)"
        input2Layout.hint = "Lebar (cm)"
        input3Layout.hint = "Tinggi (cm)"
        input3Layout.visibility = android.view.View.VISIBLE

        // Update satuan
        tvSatuan.text = "cm³"
    }

    private fun resetResult() {
        tvFormula.text = ""
        tvHasil.text = "0.00"
    }

    private fun performCalculation() {
        try {
            val selectedId = radioGroup.checkedRadioButtonId
            var result = 0.0
            var formula = ""

            when (selectedId) {
                R.id.radioPersegiPanjang -> {
                    val panjangText = input1.text.toString()
                    val lebarText = input2.text.toString()

                    if (panjangText.isEmpty() || lebarText.isEmpty()) {
                        Log.w(TAG, "WARNING: Mencoba menghitung dengan input kosong!")
                        showError("Masukkan panjang dan lebar!")
                        return
                    }

                    val panjang = panjangText.toDouble()
                    val lebar = lebarText.toDouble()

                    if (panjang <= 0 || lebar <= 0) {
                        Log.e(TAG, "ERROR: Input tidak masuk akal (angka <= 0)!")
                        showError("Nilai harus lebih dari 0!")
                        return
                    }

                    result = perhitungan.hitungLuasPersegiPanjang(panjang, lebar)
                    formula = perhitungan.getFormulaLuasPersegiPanjang(panjang, lebar)
                }

                R.id.radioBalok -> {
                    val panjangText = input1.text.toString()
                    val lebarText = input2.text.toString()
                    val tinggiText = input3.text.toString()

                    if (panjangText.isEmpty() || lebarText.isEmpty() || tinggiText.isEmpty()) {
                        Log.w(TAG, "WARNING: Input Balok tidak lengkap!")
                        showError("Masukkan panjang, lebar, dan tinggi!")
                        return
                    }

                    val panjang = panjangText.toDouble()
                    val lebar = lebarText.toDouble()
                    val tinggi = tinggiText.toDouble()

                    if (panjang <= 0 || lebar <= 0 || tinggi <= 0) {
                        Log.e(TAG, "ERROR: Input Balok mengandung angka <= 0!")
                        showError("Semua nilai harus lebih dari 0!")
                        return
                    }

                    result = perhitungan.hitungVolumeBalok(panjang, lebar, tinggi)
                    formula = perhitungan.getFormulaVolumeBalok(panjang, lebar, tinggi)
                }

                else -> {
                    showError("Pilih jenis bangun terlebih dahulu!")
                    return
                }
            }

            tvFormula.text = formula
            val hasilText = if (result == result.toLong().toDouble()) {
                String.format("%.0f", result)
            } else {
                String.format("%.2f", result)
            }
            tvHasil.text = hasilText

            Log.i(TAG, "INFO: Perhitungan berhasil: $hasilText")

        } catch (e: NumberFormatException) {
            Log.e(TAG, "EXCEPTION: Format input salah/bukan angka! Detail: ${e.message}")
            showError("Masukkan angka yang valid!")
        } catch (e: Exception) {
            Log.e(TAG, "FATAL ERROR: ${e.message}")
            showError("Terjadi kesalahan: ${e.message}")
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}