package com.example.zequiz.dataApi

import com.example.zequiz.model.JawabanSiswa
import com.example.zequiz.model.LoginRequest
import com.example.zequiz.model.LoginResponse
import com.example.zequiz.model.RegisterRequest
import com.example.zequiz.model.RegisterResponse
import com.example.zequiz.model.Soal
import com.example.zequiz.model.Topik
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): retrofit2.Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("zequiz/topic")
    fun getSoalTopik(): Call<List<Topik>>

    @GET("zequiz/soal/{topikId}")
    fun getSoal(@Path("topikId") id : Int): Call<List<Soal>>

    @POST("zequiz/krim")
    fun kirimJawaban(@Body jawaban: List<JawabanSiswa>): Call<>
}