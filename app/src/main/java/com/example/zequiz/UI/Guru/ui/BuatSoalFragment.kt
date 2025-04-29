package com.example.zequiz.UI.Guru.ui.buatsoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zequiz.R
import com.example.zequiz.databinding.FragmentBuatSoalBinding
import com.example.zequiz.model.Soal

class BuatSoalFragment : Fragment() {

    private var _binding: FragmentBuatSoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuatSoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        // 🔥 Klik tombol Simpan
        binding.btnsmpn.setOnClickListener {
            simpanSoal()
        }

        // 🔥 Klik tombol Back
        binding.menuBack.setOnClickListener {
            // Munculkan BottomNavigationView
            requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE

            // Balik ke BankSoalFragment
            findNavController().navigate(R.id.navigation_banksoal)
        }
    }

    private fun simpanSoal() {
        val pertanyaan = binding.soal.text.toString()
        val opsiA = binding.opsiA.text.toString()
        val opsiB = binding.opsiB.text.toString()
        val opsiC = binding.opsiC.text.toString()
        val opsiD = binding.opsiD.text.toString()
        val jawabanBenar = binding.opsiBenar.text.toString()

        if (pertanyaan.isBlank() || opsiA.isBlank() || opsiB.isBlank() ||
            opsiC.isBlank() || opsiD.isBlank() || jawabanBenar.isBlank()
        ) {
            Toast.makeText(requireContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        val soalBaru = Soal(
            id = 0,
            topikId = 0,
            gambar = "",
            pertanyaan = pertanyaan,
            pilihanA = opsiA,
            pilihanB = opsiB,
            pilihanC = opsiC,
            pilihanD = opsiD,
            jawabanBenar = jawabanBenar
        )

        val bundle = Bundle()
        bundle.putParcelable("soalBaru", soalBaru)

        // Munculkan BottomNavigationView lagi
        requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.VISIBLE

        // Navigasi balik ke BankSoalFragment bawa soal
        findNavController().navigate(R.id.navigation_banksoal, bundle)

        Toast.makeText(requireContext(), "Soal berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
