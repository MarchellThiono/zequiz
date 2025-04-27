package com.example.zequiz.UI.Siswa

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.UI.LoginActivity
import com.example.zequiz.adapter.ListKuisAdapter
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.model.Kuis
import com.example.zequiz.model.ResponseAllKuis
import com.example.zequiz.model.Soal
import com.example.zequiz.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSiswaFragment : Fragment(), View.OnClickListener {

    private lateinit var rvTopik: RecyclerView
    private lateinit var listKuis: ArrayList<Kuis>
    private lateinit var emptyStateText: View
    private lateinit var textNama: TextView
    private lateinit var textKelas: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_siswa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTopik = view.findViewById(R.id.tv_topik)
        emptyStateText = view.findViewById(R.id.tv_empty_state)
        val btnLogout = view.findViewById<ImageView>(R.id.btn_logout)

        textNama = view.findViewById(R.id.nm_user)
        textKelas = view.findViewById(R.id.kelas)

        val sessionManager = SessionManager(requireContext())
        val nama = sessionManager.getUsername() ?: "User"
        val kelas = sessionManager.getKelas()

        textNama.text = "Halo, $nama"
        textKelas.text = "Kelas $kelas"

        btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        rvTopik.setHasFixedSize(true)
        rvTopik.layoutManager = LinearLayoutManager(requireContext())
        listKuis = ArrayList()

        fetchKuisFromServer()
    }

    private fun showLogoutDialog() {
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

    private fun fetchKuisFromServer() {
        val apiService = ApiClient.instance

        apiService.getAllKuis(4).enqueue(object : Callback<ResponseAllKuis> {
            override fun onResponse(
                call: Call<ResponseAllKuis>,
                response: Response<ResponseAllKuis>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    listKuis.clear()

                    response.body()?.responseAllKuis?.forEach { item ->
                        if (item != null) {
                            val kuis = Kuis(
                                id = item.id?.toLong() ?: 0L,
                                timer = item.timer ?: 0,
                                jumlahSoal = item.jumlahSoal ?: 0,
                                tanggal = item.tanggal ?: "",
                                topikNama = item.topik?.nama ?: "",
                                guruNama = item.guru?.username ?: "",
                                topikId = item.topik?.id ?: 0,
                                kelasNama = item.kelas?.nama ?: ""
                            )
                            listKuis.add(kuis)
                        }
                    }

                    if (listKuis.isEmpty()) {
                        rvTopik.visibility = View.GONE
                        emptyStateText.visibility = View.VISIBLE
                    } else {
                        rvTopik.visibility = View.VISIBLE
                        emptyStateText.visibility = View.GONE

                        val adapter = ListKuisAdapter(
                            listKuis,
                            showStatus = true
                        ) { kuis ->
                            // Klik item kuis
                            AlertDialog.Builder(requireContext())
                                .setTitle("Konfirmasi")
                                .setMessage("Apakah kamu sudah siap untuk mengerjakan soal ini?")
                                .setPositiveButton("Ya") { _, _ ->
                                    fetchSoalFromServer(kuis.topikId, kuis.timer)
                                }
                                .setNegativeButton("Tidak", null)
                                .show()
                        }
                        rvTopik.adapter = adapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal ambil data kuis", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseAllKuis>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchSoalFromServer(topikId: Int, timerMenit: Int) {
        val apiService = ApiClient.instance

        apiService.getSoalByTopik(topikId).enqueue(object : Callback<List<Soal>> {
            override fun onResponse(call: Call<List<Soal>>, response: Response<List<Soal>>) {
                if (response.isSuccessful && response.body() != null) {
                    val soalList = response.body()!!

                    val fragment = DetailSoalFragment().apply {
                        arguments = Bundle().apply {
                            putParcelableArrayList("LIST_SOAL", ArrayList(soalList))
                            putLong("WAKTU_SOAL", timerMenit * 60_000L) // Timer menit ➔ milidetik
                        }
                    }

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "Gagal ambil soal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Soal>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        // Belum ada click tambahan
    }
}
