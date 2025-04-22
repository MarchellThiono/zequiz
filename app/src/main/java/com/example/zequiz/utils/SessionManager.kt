package com.example.zequiz.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("zequiz_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USERNAME = "username"
        private const val KEY_KELAS = "kelas"
    }

    // Simpan token JWT
    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    // Ambil token
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    // Hapus semua data session (biasanya saat logout)
    fun clearToken() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    // Simpan informasi user (username dan kelas sebagai String)
    fun saveUserInfo(username: String, kelas: String) {
        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_KELAS, kelas)
        editor.apply()
    }

    // Ambil nama user
    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    // Ambil kelas user
    fun getKelas(): String? {
        return prefs.getString(KEY_KELAS, null)
    }
}
