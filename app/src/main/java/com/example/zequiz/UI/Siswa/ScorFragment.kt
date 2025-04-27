package com.example.zequiz.UI.Siswa

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.zequiz.R
import com.example.zequiz.utils.SessionManager

class ScorFragment : Fragment(), View.OnClickListener {

    private lateinit var tvNamaMurid: TextView
    private lateinit var tvKelasMurid: TextView
    private lateinit var tvSkorAkhir: TextView
    private lateinit var tvJudulKuis: TextView
    private lateinit var btnKembali: Button

    private var skorAkhir: Int = 0
    private var jumlahSoal: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvNamaMurid = view.findViewById(R.id.tv_namaMurid)
        tvKelasMurid = view.findViewById(R.id.tv_kelasMurid)
        tvSkorAkhir = view.findViewById(R.id.tv_skorAkhir)
        tvJudulKuis = view.findViewById(R.id.tv_judulKuis)
        btnKembali = view.findViewById(R.id.btn_kembali)

        btnKembali.setOnClickListener(this)

        // Ambil data skor
        skorAkhir = arguments?.getInt("SKOR") ?: 0
        jumlahSoal = arguments?.getInt("JUMLAH_SOAL") ?: 0

        tampilkanData()
    }

    private fun tampilkanData() {
        val sessionManager = SessionManager(requireContext())
        val nama = sessionManager.getUsername() ?: "Siswa"
        val kelas = sessionManager.getKelas()

        tvNamaMurid.text = nama
        tvKelasMurid.text = "Kelas $kelas"

        tvJudulKuis.text = "Skor Anda ($skorAkhir / ${jumlahSoal * 10})"

        // 🔥 Tambahkan Warning kalau belum jawab semua soal
        val skorMaksimal = jumlahSoal * 10
        if (skorAkhir < skorMaksimal) {
            Toast.makeText(
                requireContext(),
                "Kamu tidak menjawab semua soal, skor mungkin lebih rendah!",
                Toast.LENGTH_LONG
            ).show()
        }

        // 🔥 Animasikan skor dari 0 ke skorAkhir
        animateScore(0, skorAkhir)
    }

    private fun animateScore(start: Int, end: Int) {
        val totalDuration = 1500L // 1.5 detik
        val interval = 30L

        val steps = ((end - start).coerceAtLeast(1))
        val timePerStep = totalDuration / steps

        object : CountDownTimer(totalDuration, interval) {
            var current = start

            override fun onTick(millisUntilFinished: Long) {
                if (current <= end) {
                    tvSkorAkhir.text = current.toString()
                    current += 1
                }
            }

            override fun onFinish() {
                tvSkorAkhir.text = end.toString()
            }
        }.start()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_kembali) {
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, HomeSiswaFragment(), HomeSiswaFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }
}
