package com.example.fajar_time.Pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fajar_time.databinding.FragmentTabPerempuanBinding

class TabPerempuanFragment : Fragment() {

    private var _binding: FragmentTabPerempuanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTabPerempuanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val perempuanList = generateDummyWarga().filter { it.jenisKelamin == "Perempuan" }

        val adapter = WargaAdapter(perempuanList)
        binding.rvWargaPerempuan.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateDummyWarga(): List<WargaModel> {
        val list = mutableListOf<WargaModel>()
        for (i in 1..50) {
            val jenis = if (i % 2 == 0) "Laki-laki" else "Perempuan"
            list.add(
                WargaModel(
                    nama = "Warga $jenis $i",
                    umur = 17 + (i % 60),
                    alamat = "RT ${i % 5 + 1}/RW ${i % 3 + 1}, Desa Bina",
                    jenisKelamin = jenis
                )
            )
        }
        return list
    }
}