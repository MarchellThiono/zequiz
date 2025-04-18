package com.example.zequiz.dataApi

import com.example.zequiz.model.LoginRequest
import com.example.zequiz.model.LoginResponse
import com.example.zequiz.model.RegisterRequest
import com.example.zequiz.model.RegisterResponse

import retrofit2.Call
import retrofit2.http.Body

import retrofit2.http.POST


interface ApiService {
    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest): retrofit2.Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

}