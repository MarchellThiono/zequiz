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

    // Simpan token
    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    // Ambil token
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    // Hapus token dan data lain (biasanya untuk logout)
    fun clearToken() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    // ✅ Simpan data user (nama dan kelas)
    fun saveUserInfo(username: String, kelas: Int) {
        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.putInt(KEY_KELAS, kelas)
        editor.apply()
    }

    // ✅ Ambil nama user
    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    // ✅ Ambil kelas user
    fun getKelas(): Int {
        return prefs.getInt(KEY_KELAS, 0)
    }
}
