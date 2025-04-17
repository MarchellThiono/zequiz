package com.example.zequiz.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("zequiz_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "jwt_token"
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

    // Hapus token (biasanya untuk logout)
    fun clearToken() {
        val editor = prefs.edit()
        editor.remove(KEY_TOKEN)
        editor.apply()
    }
}