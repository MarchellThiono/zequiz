package com.example.zequiz.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zequiz.R
import com.example.zequiz.utils.SessionManager

class MainGuru1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_guru1)

        // Mengatur padding untuk menjaga konten tetap tidak tertutup oleh system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Menambahkan aksi pada tombol logout
        val btnLogout = findViewById<ImageView>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            val sessionManager = SessionManager(this)
            sessionManager.clearToken() // Menghapus token JWT

            // Mengarahkan pengguna ke LoginActivity setelah logout
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Memastikan aktivitas ini tidak bisa kembali setelah logout
        }
    }
}
