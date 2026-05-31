package com.example.fajar_time.Pertemuan_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fajar_time.databinding.ItemWargaBinding

class WargaAdapter(private val wargaList: List<WargaModel>) :
    RecyclerView.Adapter<WargaAdapter.WargaViewHolder>() {

    inner class WargaViewHolder(val binding: ItemWargaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WargaViewHolder {
        val binding = ItemWargaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WargaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WargaViewHolder, position: Int) {
        val warga = wargaList[position]
        with(holder.binding) {
            tvNama.text = warga.nama
            tvUmur.text = "Umur: ${warga.umur} tahun"
            tvAlamat.text = warga.alamat
        }
    }

    override fun getItemCount(): Int = wargaList.size
}