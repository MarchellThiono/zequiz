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
import com.example.zequiz.adapter.ListKuisGuruAdapter
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.databinding.FragmentBerandaBinding
import com.example.zequiz.model.ListKuisResItem
import com.example.zequiz.UI.liatscor.LiatScorFragment
import com.example.zequiz.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    private lateinit var kuisList: ArrayList<ListKuisResItem>

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

        // Setup RecyclerView
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
        binding.kelas.text = if (kelas.startsWith("Kelas")) kelas else "$kelas"
    }

    private fun setupLogoutButton() {
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Konfirmasi Logout")
                setMessage("Apakah Anda yakin ingin keluar?")
                setPositiveButton("Ya") { _, _ ->
                    SessionManager(requireContext()).clearToken()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                show()
            }
        }
    }

    private fun fetchKuisGuru() {
        val apiService = ApiClient.instance
        val sessionManager = SessionManager(requireContext())
        val token = "Bearer ${sessionManager.getToken()}"

        // Sesuaikan kelasId jika perlu (bisa ambil dari SessionManager)
        apiService.getAllKuis(4, token)
            .enqueue(object : Callback<List<ListKuisResItem>> {
                override fun onResponse(
                    call: Call<List<ListKuisResItem>>,
                    response: Response<List<ListKuisResItem>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        kuisList.clear()
                        kuisList.addAll(response.body()!!)

                        if (kuisList.isNotEmpty()) {
                            val adapter = ListKuisGuruAdapter(kuisList) { kuis ->
                                val fragment = LiatScorFragment().apply {
                                    arguments = Bundle().apply {
                                        putInt("kuisId", kuis.id ?: 0)
                                    }
                                }

                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, fragment)
                                    .addToBackStack(null)
                                    .commit()
                            }
                            binding.tvTopik.adapter = adapter
                        } else {
                            binding.tvEmptyState.visibility = View.VISIBLE
                            binding.tvTopik.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(requireContext(), "Gagal ambil data kuis", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<ListKuisResItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
