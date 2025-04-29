package com.example.zequiz.UI.Guru.ui.banksoal

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zequiz.R
import com.example.zequiz.adapter.SoalAdapter
import com.example.zequiz.databinding.FragmentBankSoalBinding
import com.example.zequiz.model.Soal

class BankSoalFragment : Fragment() {

    private var _binding: FragmentBankSoalBinding? = null
    private val binding get() = _binding!!

    private lateinit var soalAdapter: SoalAdapter
    private val listSoal = ArrayList<Soal>()

    // Topik untuk dropdown
    private val topikList = arrayListOf("Bab 1: Pecahan")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankSoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinnerTopik()

        val soalBaru = arguments?.getParcelable<Soal>("soalBaru")
        soalBaru?.let {
            listSoal.add(0, it)
            soalAdapter.notifyItemInserted(0)
            binding.rvSoal.scrollToPosition(0)
            Toast.makeText(requireContext(), "Soal baru ditambahkan!", Toast.LENGTH_SHORT).show()
        }

        binding.menuAdd.setOnClickListener {
            showAddTopikDialog()
        }

        binding.fabTambahSoal.setOnClickListener {
            requireActivity().findViewById<View>(R.id.nav_view)?.visibility = View.GONE
            findNavController().navigate(R.id.buatSoalFragment)
        }
    }

    private fun setupRecyclerView() {
        soalAdapter = SoalAdapter(listSoal) { soal ->
            showDeleteConfirmation(soal)
        }
        binding.rvSoal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = soalAdapter
        }
    }

    private fun setupSpinnerTopik() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, topikList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dropdownField.setAdapter(adapter)
        binding.dropdownField.setText(topikList[0], false)

        // Nonaktifkan input ketik
        binding.dropdownField.inputType = InputType.TYPE_NULL
        binding.dropdownField.keyListener = null
        binding.dropdownField.isFocusable = false
        binding.dropdownField.isCursorVisible = false

        // Saat diklik, munculkan dropdown
        binding.dropdownField.setOnClickListener {
            binding.dropdownField.showDropDown()
        }
    }
    private fun showAddTopikDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Tambah Topik Baru")

        val input = EditText(requireContext())
        input.hint = "Contoh: Bab 2: Aritmatika"
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setPadding(40, 30, 40, 30)

        builder.setView(input)

        builder.setPositiveButton("Tambah") { dialog, _ ->
            val topikBaru = input.text.toString().trim()
            if (topikBaru.isNotEmpty()) {
                topikList.add(topikBaru)
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, topikList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.dropdownField.setAdapter(adapter)
                binding.dropdownField.setText(topikBaru, false)
                Toast.makeText(requireContext(), "Topik \"$topikBaru\" berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Topik tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showDeleteConfirmation(soal: Soal) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Soal")
            .setMessage("Yakin ingin menghapus soal ini?")
            .setPositiveButton("Hapus") { _, _ ->
                val index = listSoal.indexOf(soal)
                if (index != -1) {
                    listSoal.removeAt(index)
                    soalAdapter.notifyItemRemoved(index)
                    Toast.makeText(requireContext(), "Soal berhasil dihapus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
