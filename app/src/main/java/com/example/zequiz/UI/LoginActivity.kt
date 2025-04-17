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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zequiz.R
import com.example.zequiz.dataApi.ApiClient
import com.example.zequiz.databinding.ActivityLoginBinding
import com.example.zequiz.model.LoginRequest
import com.example.zequiz.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var username : EditText
    private lateinit var password : EditText
    private lateinit var btnlogin : Button
    private lateinit var btndaftar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        username = findViewById(R.id.input_username)
        password = findViewById(R.id.input_KataSandi)
        btnlogin = findViewById(R.id.button_login)
        btndaftar = findViewById(R.id.button_daftar)
        btnlogin.setOnClickListener(this)

        supportActionBar?.hide()

        binding.buttonDaftar.setOnClickListener {
            startActivity(Intent(this@LoginActivity, DaftarActivity ::class.java))
        }

    }

    override fun onClick(view: View?) {
        val inputnama = username.text.toString().trim()
        val inputpas = password.text.toString().trim()
        var isEmptyFields = false
        if (inputnama.isEmpty()){
            isEmptyFields = true
            username.error = "Tidak boleh kosong"
        }
        if (inputpas.isEmpty()){
            isEmptyFields = true
            password.error = "Tidak boleh Kosong"
        }
        if (!isEmptyFields){
         loginUser(inputnama, inputpas)
        }
    }
    private fun loginUser(username : String, KataSandi: String){
        val loginRequest = LoginRequest(username, KataSandi)
        val apiService = ApiClient.instance

        apiService.loginUser(loginRequest).enqueue(object :Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null){
                    val token = response.body()!!.token
                    Toast.makeText(this@LoginActivity, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                    Log.d("LoginToken", "JWT Token: $token")

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }else {
                    Toast.makeText(this@LoginActivity,"Login gagal: ${response.message()}",Toast.LENGTH_SHORT).show()
                }

            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
              Toast.makeText(this@LoginActivity,"Error: ${t.message}",Toast.LENGTH_SHORT).show()
            }

        })
    }

}