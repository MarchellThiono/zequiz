package com.example.zequiz.UI.Siswa

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.zequiz.R
import com.example.zequiz.model.SoalRes
import java.util.concurrent.TimeUnit

class DetailSoalFragment : Fragment() {

    private lateinit var listSoal: List<SoalRes>
    private var indexSaatIni = 0
    private val jawabanUser = mutableMapOf<Int, String>()

    private var timer: CountDownTimer? = null
    private var waktuMillis: Long = 60 * 60 * 1000 // Default 1 jam

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_soal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari Bundle
        listSoal = arguments?.getParcelableArrayList("LIST_SOAL") ?: emptyList()
        waktuMillis = arguments?.getLong("WAKTU_SOAL", waktuMillis) ?: waktuMillis

        tampilkanSoal(view)
        mulaiTimer(view)

        view.findViewById<Button>(R.id.btn_Berikutnya).setOnClickListener {
            simpanJawaban(view)
            if (indexSaatIni < listSoal.lastIndex) {
                indexSaatIni++
                tampilkanSoal(view)
            } else {
                kirimJawaban()
            }
        }

        view.findViewById<Button>(R.id.btn_Kembali).setOnClickListener {
            simpanJawaban(view)
            if (indexSaatIni > 0) {
                indexSaatIni--
                tampilkanSoal(view)
            }
        }
    }

    private fun mulaiTimer(view: View) {
        val txtWaktu = view.findViewById<TextView>(R.id.waktu)

        timer = object : CountDownTimer(waktuMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val jam = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val menit = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val detik = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                txtWaktu.text = String.format("%02d:%02d:%02d", jam, menit, detik)
            }

            override fun onFinish() {
                Toast.makeText(requireContext(), "Waktu habis!", Toast.LENGTH_SHORT).show()
                kirimJawaban()
            }
        }.start()
    }


    private fun tampilkanSoal(view: View) {
        val soal = listSoal[indexSaatIni]

        view.findViewById<TextView>(R.id.tvSoal).text = soal.pertanyaan
        view.findViewById<RadioButton>(R.id.rbA).text = soal.pilihanA
        view.findViewById<RadioButton>(R.id.rbB).text = soal.pilihanB
        view.findViewById<RadioButton>(R.id.rbC).text = soal.pilihanC
        view.findViewById<RadioButton>(R.id.rbD).text = soal.pilihanD

        val radioGroup = view.findViewById<RadioGroup>(R.id.rgJawaban)
        radioGroup.clearCheck()

        jawabanUser[indexSaatIni]?.let {
            val selectedRadio = when (it) {
                soal.pilihanA -> R.id.rbA
                soal.pilihanB -> R.id.rbB
                soal.pilihanC -> R.id.rbC
                soal.pilihanD -> R.id.rbD
                else -> -1
            }
            if (selectedRadio != -1) radioGroup.check(selectedRadio)
        }

        // Update tombol Kembali / Kirim
        view.findViewById<Button>(R.id.btn_Kembali).visibility =
            if (indexSaatIni == 0) View.GONE else View.VISIBLE

        view.findViewById<Button>(R.id.btn_Berikutnya).text =
            if (indexSaatIni == listSoal.lastIndex) "Kirim" else "Berikutnya"

        view.findViewById<TextView>(R.id.jlm_soal).text =
            "Pertanyaan ${indexSaatIni + 1} / ${listSoal.size}"

        val imgSoal = view.findViewById<ImageView>(R.id.imgSoal)
        val imageUrl = soal.gambar

        if (!imageUrl.isNullOrEmpty()) {
            imgSoal.visibility = View.VISIBLE
            Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.placeholder_image)
                .into(imgSoal)
        } else {
            imgSoal.visibility = View.GONE
        }
    }

    private fun simpanJawaban(view: View) {
        val selectedId = view.findViewById<RadioGroup>(R.id.rgJawaban).checkedRadioButtonId
        if (selectedId != -1) {
            val selectedRadio = view.findViewById<RadioButton>(selectedId)
            jawabanUser[indexSaatIni] = selectedRadio.text.toString()
        }
    }

    private fun kirimJawaban() {
        timer?.cancel()

        val skor = hitungSkor()

        val bundle = Bundle().apply {
            putInt("SKOR", skor)
            putInt("JUMLAH_SOAL", listSoal.size)
        }

        val fragment = ScorFragment().apply {
            arguments = bundle
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun hitungSkor(): Int {
        var skor = 0
        listSoal.forEachIndexed { index, soal ->
            if (jawabanUser[index] == soal.jawabanBenar) {
                skor += 10
            }
        }
        return skor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}
