package com.example.zequiz.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.zequiz.R
import com.example.zequiz.UI.Guru.ui.MainGuruActivity
import com.example.zequiz.UI.Siswa.MainActivity
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.databinding.ActivityLoginBinding
import com.example.zequiz.model.LoginRequest
import com.example.zequiz.model.LoginResponse
import com.example.zequiz.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btnlogin: Button
    private lateinit var btndaftar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah token sudah ada
        val sessionManager = SessionManager(applicationContext)
        val token = sessionManager.getToken()

        if (token != null) {
            val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
            val role = sharedPref.getString("ROLE", null)
            when (role) {
                "SISWA" -> startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                "GURU" -> startActivity(Intent(this@LoginActivity, MainGuruActivity::class.java))
            }
            finish()
        }

        username = findViewById(R.id.input_username)
        password = findViewById(R.id.input_KataSandi)
        btnlogin = findViewById(R.id.button_login)
        btndaftar = findViewById(R.id.button_daftar)
        btnlogin.setOnClickListener(this)

        binding.buttonDaftar.setOnClickListener {
            startActivity(Intent(this@LoginActivity, DaftarActivity::class.java))
        }
    }

    override fun onClick(view: View?) {
        val inputnama = username.text.toString().trim()
        val inputpas = password.text.toString().trim()
        var isEmptyFields = false

        if (inputnama.isEmpty()) {
            isEmptyFields = true
            username.error = "Tidak boleh kosong"
        }
        if (inputpas.isEmpty()) {
            isEmptyFields = true
            password.error = "Tidak boleh kosong"
        }
        if (!isEmptyFields) {
            loginUser(inputnama, inputpas)
        }
    }

    private fun loginUser(username: String, kata_sandi: String) {
        val loginRequest = LoginRequest(username, kata_sandi)
        val apiService = ApiClient.instance

        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    val token = loginResponse.token
                    val role = loginResponse.role
                    val namaUser = loginResponse.username
                    val kelasUser = loginResponse.kelas

                    Toast.makeText(this@LoginActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    Log.d("LoginToken", "JWT Token: $token")
                    Log.d("LoginRole", "User Role: $role")

                    val sessionManager = SessionManager(applicationContext)
                    sessionManager.saveToken(token)
                    sessionManager.saveUserInfo(namaUser, kelasUser)

                    val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("ROLE", role)
                        apply()
                    }

                    when (role) {
                        "SISWA" -> startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        "GURU" -> startActivity(Intent(this@LoginActivity, MainGuruActivity::class.java))
                        else -> Toast.makeText(this@LoginActivity, "Role tidak dikenali", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Nama atau kata sandi salah! ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
