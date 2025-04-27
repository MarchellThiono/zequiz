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
import com.example.zequiz.UI.LoginActivity
import com.example.zequiz.adapter.ListKuisAdapter
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.databinding.FragmentBerandaBinding
import com.example.zequiz.model.Kuis
import com.example.zequiz.model.ResponseAllKuis
import com.example.zequiz.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    private lateinit var kuisList: ArrayList<Kuis>

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

        binding.rvKuisGuru.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKuisGuru.setHasFixedSize(true)

        setupUserData()
        setupLogoutButton()
        fetchKuisGuru()
    }

    private fun setupUserData() {
        val sessionManager = SessionManager(requireContext())
        val nama = sessionManager.getUsername() ?: "User"
        val kelas = sessionManager.getKelas()

        binding.nmUser.text = "Halo, $nama"
        binding.kelas.text = "Kelas $kelas"
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

                        response.body()?.responseAllKuis?.forEach { item ->
                            if (item != null) {
                                val kuis = Kuis(
                                    id = item.id?.toLong() ?: 0L,
                                    timer = item.timer ?: 0,
                                    jumlahSoal = item.jumlahSoal ?: 0,
                                    tanggal = item.tanggal ?: "",
                                    topikNama = item.topik?.nama ?: "",
                                    guruNama = item.guru?.username ?: "",
                                    kelasNama = item.kelas?.nama ?: ""
                                )
                                kuisList.add(kuis)
                            }
                        }

                        val adapter = ListKuisAdapter(
                            kuisList,
                            showStatus = false
                        ) { kuis ->
                            // Klik kuis Guru
                            // Nanti bisa tambah navigasi ke lihat siswa
                        }

                        binding.rvKuisGuru.adapter = adapter
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
