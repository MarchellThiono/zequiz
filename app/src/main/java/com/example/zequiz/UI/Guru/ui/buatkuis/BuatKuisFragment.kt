package com.example.zequiz.UI.Guru.ui.buatkuis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.zequiz.R
import com.example.zequiz.databinding.FragmentBuatKuisBinding
import kotlinx.coroutines.launch

class BuatKuisFragment : Fragment() {

    private var _binding: FragmentBuatKuisBinding? = null
    private val binding get() = _binding!!

    private val kuisViewModel: BuatKuisViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuatKuisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.GONE

        setupSpinner()

        binding.menuBack.setOnClickListener {
            navigateToBeranda()
        }

        binding.btntmbh.setOnClickListener {
            val namaKuis = binding.namakuis.text.toString().trim()
            val jumlahSoal = binding.spinnerJumlahsoal.selectedItem?.toString()?.trim() ?: ""
            val waktu = binding.spinnerWaktu.selectedItem?.toString()?.trim() ?: ""
            val topik = binding.spinnerTopik.selectedItem?.toString()?.trim() ?: ""

            if (namaKuis.isEmpty() || jumlahSoal.isEmpty() || waktu.isEmpty() || topik.isEmpty()) {
                Toast.makeText(requireContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = Bundle().apply {
                    putString("nama_kuis", namaKuis)
                    putString("jumlah_soal", jumlahSoal)
                    putString("waktu", waktu)
                    putString("topik", topik)
                }
                findNavController().navigate(R.id.navigation_home, bundle)
                Toast.makeText(requireContext(), "Kuis berhasil dibuat!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSpinner() {
        val jumlahSoalAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.jumlahsoal, android.R.layout.simple_spinner_item
        )
        jumlahSoalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerJumlahsoal.adapter = jumlahSoalAdapter

        val waktuAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.waktukuis, android.R.layout.simple_spinner_item
        )
        waktuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerWaktu.adapter = waktuAdapter

        kuisViewModel.topikList.observe(viewLifecycleOwner) { list ->
            val namaTopik = list.map { it.nama }
            val topikAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, namaTopik)
            topikAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerTopik.adapter = topikAdapter
        }

        kuisViewModel.fetchTopik()
    }

    private fun navigateToBeranda() {
        findNavController().navigate(R.id.navigation_home)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE
    }
}
