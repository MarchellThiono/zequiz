package com.example.zequiz.model

data class LoginResponse(
    val token: String,
    val role: String,
    val message: String,
    val username: String,
    val kelas: Int
)
