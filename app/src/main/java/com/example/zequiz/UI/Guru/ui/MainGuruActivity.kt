package com.example.zequiz.UI

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.zequiz.R
import com.example.zequiz.databinding.ActivityMainGuruBinding
import com.example.zequiz.utils.SessionManager

class MainGuruActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainGuruBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainGuruBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main_guru)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_add,
                R.id.navigation_banksoal
            )
        )

        // 🔥 Pasang BottomNavigationView
        binding.navView.setupWithNavController(navController)

        // ✅ Tambahkan aksi Logout
        val btnLogout = findViewById<ImageView>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            val sessionManager = SessionManager(this)
            sessionManager.clearToken() // Hapus token JWT

            // Arahkan balik ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Tutup activity biar gak bisa klik back
        }
    }
}