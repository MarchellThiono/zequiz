package com.example.zequiz.UI

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.adapter.ListTopikAdapter
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.model.Topik
import com.example.zequiz.model.Soal
import com.example.zequiz.ui.DetailSoalFragment
import com.example.zequiz.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeSiswaFragment : Fragment(), View.OnClickListener {

    private lateinit var rvTopik: RecyclerView
    private lateinit var list: ArrayList<Topik>
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
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah Anda yakin ingin keluar?")
            builder.setPositiveButton("Ya") { _, _ ->
                sessionManager.clearToken()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            builder.show()
        }

        rvTopik.setHasFixedSize(true)
        rvTopik.layoutManager = LinearLayoutManager(requireContext())
        list = ArrayList()

        fetchTopikFromServer()
    }

    private fun fetchTopikFromServer() {
        // Langsung menggunakan ApiClient.instance tanpa create(ApiService::class.java)
        val apiService = ApiClient.instance

        apiService.getAllTopik().enqueue(object : Callback<List<Topik>> {
            override fun onResponse(call: Call<List<Topik>>, response: Response<List<Topik>>) {
                if (response.isSuccessful && response.body() != null) {
                    list.clear()
                    list.addAll(response.body()!!)

                    if (list.isEmpty()) {
                        rvTopik.visibility = View.GONE
                        emptyStateText.visibility = View.VISIBLE
                    } else {
                        rvTopik.visibility = View.VISIBLE
                        emptyStateText.visibility = View.GONE

                        val adapter = ListTopikAdapter(list) { topik ->
                            AlertDialog.Builder(requireContext())
                                .setTitle("Konfirmasi")
                                .setMessage("Apakah kamu sudah siap untuk mengerjakan soal ini?")
                                .setPositiveButton("Ya") { _, _ ->
                                    fetchSoalFromServer(topik.id)
                                }
                                .setNegativeButton("Tidak", null)
                                .show()
                        }
                        rvTopik.adapter = adapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal ambil data topik", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Topik>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchSoalFromServer(topikId: Int) {
        // Langsung menggunakan ApiClient.instance tanpa create(ApiService::class.java)
        val apiService = ApiClient.instance

        apiService.getSoalByTopik(topikId).enqueue(object : Callback<List<Soal>> {
            override fun onResponse(call: Call<List<Soal>>, response: Response<List<Soal>>) {
                if (response.isSuccessful && response.body() != null) {
                    val soalList = response.body()!!

                    // Membuat fragment dan mengirimkan data ke fragment
                    val fragment = DetailSoalFragment().apply {
                        arguments = Bundle().apply {
                            putParcelableArrayList("LIST_SOAL", ArrayList(soalList))
                            putLong("WAKTU_SOAL", 3600000L) // waktu 1 jam
                        }
                    }

                    // Menggunakan FragmentTransaction untuk mengganti fragment
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment) // ganti dengan ID container kamu
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
        // Tidak ada aksi khusus untuk onClick
    }
}
