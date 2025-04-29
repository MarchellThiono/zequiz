package com.example.zequiz.dataApi

import android.content.Context
import com.example.zequiz.App
import com.example.zequiz.utils.SessionManager
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://192.168.1.5:8080/zequiz/"

    // Inisialisasi SessionManager
    private lateinit var sessionManager: SessionManager

    // Setup OkHttpClient dengan Interceptor untuk menambahkan token
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            // Ambil token dari SessionManager
            val token = sessionManager.getToken()

            // Buat request baru dengan header Authorization jika token ada
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }
        .build()

    // Inisialisasi Retrofit
    val instance: ApiService by lazy {
        // Dapatkan context dari aplikasi untuk digunakan di SessionManager
        sessionManager = SessionManager(App.getContext())  // Pastikan menggunakan context yang benar

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // Menambahkan OkHttpClient dengan interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
