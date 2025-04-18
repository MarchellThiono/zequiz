package com.example.zequiz.UI

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zequiz.R
import com.example.zequiz.adapter.ListTopikAdapter
import com.example.zequiz.model.Topik
import com.example.zequiz.utils.SessionManager

class HomeSiswaFragment : Fragment(), View.OnClickListener {

    private lateinit var rvTopik: RecyclerView
    private lateinit var list: ArrayList<Topik>
    private lateinit var emptyStateText: View

    // Tambahan: deklarasi TextView nama dan kelas
    private lateinit var textNama: TextView
    private lateinit var textKelas: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_siswa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTopik = view.findViewById(R.id.tv_topik)
        emptyStateText = view.findViewById(R.id.tv_empty_state)
        val btnLogout = view.findViewById<ImageView>(R.id.btn_logout)

        // Inisialisasi TextView nama dan kelas
        textNama = view.findViewById(R.id.nm_user)
        textKelas = view.findViewById(R.id.kelas)

        // Ambil data dari SessionManager
        val sessionManager = SessionManager(requireContext())
        val nama = sessionManager.getUsername()
        val kelas = sessionManager.getKelas()

        // Set ke TextView
        textNama.text = "Halo, $nama"
        textKelas.text = "Kelas $kelas"

        // Logout
        btnLogout.setOnClickListener {
            sessionManager.clearToken()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Setup RecyclerView
        rvTopik.setHasFixedSize(true)
        rvTopik.layoutManager = LinearLayoutManager(requireContext())
        list = ArrayList()

        fetchTopikFromServer()
    }

    private fun fetchTopikFromServer() {
        // Simulasi backend kosong
        list.clear() // data kosong, seolah belum ada soal dari guru

        if (list.isEmpty()) {
            rvTopik.visibility = View.GONE
            emptyStateText.visibility = View.VISIBLE
        } else {
            rvTopik.visibility = View.VISIBLE
            emptyStateText.visibility = View.GONE

            val adapter = ListTopikAdapter(list) { topik ->
                // Aksi klik (jika ada)
            }
            rvTopik.adapter = adapter
        }
    }

    override fun onClick(v: View?) {
        // Kosong, tidak dipakai untuk sekarang
    }
}
