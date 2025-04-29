package com.example.zequiz.dataApi

import com.example.zequiz.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // =================== AUTH ===================
    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    // =================== KUIS ===================
    @GET("kuis/kelas/{kelasId}")
    fun getAllKuis(@Path("kelasId") kelasId: Long): Call<ResponseAllKuis>  // ✅ Perbaiki disini

    // =================== SOAL ===================
    @GET("soal/topik/{id}")
    fun getSoalByTopik(@Path("id") topikId: Int): Call<List<Soal>>

    // =================== SKOR ===================
    @GET("zequiz/skor/kuis/{kuisId}")
    fun getSkorByKuis(@Path("kuisId") kuisId: Long): Call<SkorData>

    @POST("zequiz/skor/hitung")
    fun hitungSkor(
        @Body jawabanSiswaList: List<JawabanSiswa>,
        @Query("kuisId") kuisId: Long
    ): Call<SkorData>

    @GET("zequiz/skor/belum/{kuisId}")
    fun getSiswaBelum(@Path("kuisId") kuisId: Long): Call<List<SiswaBelum>>

    @GET("zequiz/skor/sudah/{kuisId}")
    fun getSiswaSudah(@Path("kuisId") kuisId: Long): Call<List<SiswaSudah>>

    companion object {
        fun getInstance(): ApiService {
            return ApiClient.instance
        }
    }
}
