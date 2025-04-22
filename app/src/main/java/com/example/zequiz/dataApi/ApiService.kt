package com.example.zequiz.dataApi

import com.example.zequiz.model.JawabanSiswa
import com.example.zequiz.model.LoginRequest
import com.example.zequiz.model.LoginResponse
import com.example.zequiz.model.RegisterRequest
import com.example.zequiz.model.RegisterResponse
import com.example.zequiz.model.SkorData
import com.example.zequiz.model.Soal
import com.example.zequiz.model.Topik
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("topik")
    fun getAllTopik(): Call<List<Topik>>

    @GET("soal/topik/{id}")
    fun getSoalByTopik(@Path("id") topikId: Int): Call<List<Soal>>

    // Mendapatkan skor berdasarkan kuis ID
    @GET("zequiz/skor/kuis/{kuisId}")
    fun getSkorByKuis(@Path("kuisId") kuisId: Long): Call<SkorData>

    // Mengirimkan skor yang sudah dihitung oleh siswa
    @POST("zequiz/skor/hitung")
    fun hitungSkor(
        @Body jawabanSiswaList: List<JawabanSiswa>,
        @Path("kuisId") kuisId: Long
    ): Call<SkorData>
}
