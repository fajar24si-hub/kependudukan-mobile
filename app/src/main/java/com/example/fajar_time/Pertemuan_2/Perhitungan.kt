package com.example.fajar_time.Pertemuan_2

/**
 * Class Perhitungan untuk menampung rumus-rumus
 * Bangun Datar: Persegi Panjang
 * Bangun Ruang: Balok
 */
class Perhitungan {

    companion object {
        private const val TAG = "Perhitungan"
    }

    // ===== BANGUN DATAR: PERSEGI PANJANG =====

    /**
     * Menghitung Luas Persegi Panjang
     * Rumus: panjang × lebar
     */
    fun hitungLuasPersegiPanjang(panjang: Double, lebar: Double): Double {
        val hasil = panjang * lebar
        return hasil
    }

    /**
     * Menghitung Keliling Persegi Panjang (opsional)
     * Rumus: 2 × (panjang + lebar)
     */
    fun hitungKelilingPersegiPanjang(panjang: Double, lebar: Double): Double {
        return 2 * (panjang + lebar)
    }

    /**
     * Mendapatkan formula Luas Persegi Panjang
     */
    fun getFormulaLuasPersegiPanjang(panjang: Double, lebar: Double): String {
        return "Luas Persegi Panjang = panjang × lebar\n= $panjang × $lebar"
    }

    // ===== BANGUN RUANG: BALOK =====

    /**
     * Menghitung Volume Balok
     * Rumus: panjang × lebar × tinggi
     */
    fun hitungVolumeBalok(panjang: Double, lebar: Double, tinggi: Double): Double {
        val hasil = panjang * lebar * tinggi
        return hasil
    }

    /**
     * Menghitung Luas Permukaan Balok (opsional)
     * Rumus: 2 × (pl + pt + lt)
     */
    fun hitungLuasPermukaanBalok(panjang: Double, lebar: Double, tinggi: Double): Double {
        return 2 * ((panjang * lebar) + (panjang * tinggi) + (lebar * tinggi))
    }

    /**
     * Mendapatkan formula Volume Balok
     */
    fun getFormulaVolumeBalok(panjang: Double, lebar: Double, tinggi: Double): String {
        return "Volume Balok = panjang × lebar × tinggi\n= $panjang × $lebar × $tinggi"
    }
}