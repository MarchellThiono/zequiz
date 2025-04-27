package com.example.zequiz.UI

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zequiz.R
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.databinding.ActivityDaftarBinding
import com.example.zequiz.model.RegisterRequest
import com.example.zequiz.model.RegisterResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDaftarBinding
    private lateinit var nama: TextInputEditText
    private lateinit var kelas: TextInputEditText
    private lateinit var katasandi: TextInputEditText
    private lateinit var konfirmasikatasandi: TextInputEditText
    private lateinit var btndaftar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nama = findViewById(R.id.msk_nm)
        kelas = findViewById(R.id.msk_kls)
        katasandi = findViewById(R.id.kataSandi1)
        konfirmasikatasandi = findViewById(R.id.kataSandi2)
        btndaftar = findViewById(R.id.btn_daftar)

        btndaftar.setOnClickListener(this)

        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setOnClickListener {
            startActivity(Intent(this@DaftarActivity, LoginActivity::class.java))
        }
    }

    override fun onClick(view: View?) {
        val inputNama = nama.text.toString().trim()
        val inputKelas = kelas.text.toString().trim()
        val inputKataSandi = katasandi.text.toString().trim()
        val inputKonfirmasiKataSandi = konfirmasikatasandi.text.toString().trim()

        if (inputNama.isEmpty() || inputKelas.isEmpty() || inputKataSandi.isEmpty() || inputKonfirmasiKataSandi.isEmpty()) {
            Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        if (inputKataSandi != inputKonfirmasiKataSandi) {
            Toast.makeText(this, "Password dan konfirmasi tidak sama", Toast.LENGTH_SHORT).show()
            return
        }

        if (inputKelas != "4" && inputKelas != "5" && inputKelas != "6") {
            Toast.makeText(this, "Kelas hanya boleh 4, 5, atau 6", Toast.LENGTH_SHORT).show()
            return
        }

        // Kalau semua validasi sudah aman, lanjut register
        registerUser(
            username = inputNama,
            kelas = inputKelas.toInt(),
            kataSandi = inputKataSandi,
            konfirmasiKataSandi = inputKonfirmasiKataSandi
        )
    }

    private fun registerUser(
        username: String,
        kelas: Int,
        kataSandi: String,
        konfirmasiKataSandi: String
    ) {
        val request = RegisterRequest(
            username = username,
            kelas = kelas,
            kata_sandi = kataSandi,
            konfirmasi_kata_sandi = konfirmasiKataSandi
        )

        val apiService = ApiClient.instance
        val call = apiService.registerUser(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@DaftarActivity, "Berhasil Daftar!", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@DaftarActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@DaftarActivity,
                        "Username telah digunakan atau error: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@DaftarActivity, "Error : ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
