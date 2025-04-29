package com.example.zequiz.UI.Guru.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zequiz.R
import com.example.zequiz.UI.LoginActivity
import com.example.zequiz.adapter.ListKuisAdapter
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.databinding.FragmentBerandaBinding
import com.example.zequiz.model.ResponseAllKuis
import com.example.zequiz.UI.liatscor.LiatScorFragment
import com.example.zequiz.model.ResponseAllKuisItem
import com.example.zequiz.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    private lateinit var kuisList: ArrayList<ResponseAllKuisItem>  // Menggunakan ResponseAllKuisItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kuisList = ArrayList()

        // Set up RecyclerView
        binding.tvTopik.layoutManager = LinearLayoutManager(requireContext())
        binding.tvTopik.setHasFixedSize(true)

        setupUserData()
        setupLogoutButton()
        fetchKuisGuru()
    }

    private fun setupUserData() {
        val sessionManager = SessionManager(requireContext())
        val nama = sessionManager.getUsername() ?: "User"
        val kelas = sessionManager.getKelas() ?: ""

        binding.nmUser.text = "Halo, $nama"
        binding.kelas.text = if (kelas.startsWith("Kelas")) {
            kelas
        } else {
            "$kelas"
        }
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah Anda yakin ingin keluar?")
            builder.setPositiveButton("Ya") { _, _ ->
                SessionManager(requireContext()).clearToken()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }
    }

    private fun fetchKuisGuru() {
        val apiService = ApiClient.instance

        apiService.getAllKuis(4) // Sesuaikan kelasId jika perlu
            .enqueue(object : Callback<ResponseAllKuis> {
                override fun onResponse(
                    call: Call<ResponseAllKuis>,
                    response: Response<ResponseAllKuis>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        kuisList.clear()

                        // Memasukkan ResponseAllKuisItem langsung ke dalam kuisList
                        response.body()?.responseAllKuis?.forEach { item ->
                            item?.let {
                                kuisList.add(it)
                            }
                        }

                        // Set up adapter jika ada kuis yang ditemukan
                        if (kuisList.isNotEmpty()) {
                            val adapter = ListKuisAdapter(
                                kuisList,  // Menggunakan kuisList yang sudah berisi ResponseAllKuisItem
                                showStatus = false
                            ) { kuis ->
                                // Klik kuis Guru
                                val fragment = LiatScorFragment().apply {
                                    arguments = Bundle().apply {
                                        putLong("kuisId", kuis.id?.toLong() ?: 0L) // Mengirimkan ID kuis
                                    }
                                }

                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, fragment)
                                    .addToBackStack(null)
                                    .commit()
                            }
                            binding.tvTopik.adapter = adapter
                        } else {
                            // Menampilkan pesan jika tidak ada kuis
                            binding.tvEmptyState.visibility = View.VISIBLE
                            binding.tvTopik.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(requireContext(), "Gagal ambil kuis guru", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseAllKuis>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
