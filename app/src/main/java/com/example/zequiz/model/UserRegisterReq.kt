package com.example.zequiz.model


data class UserRegisterReq(
    val username: String,
    val kelas: Int,
    val kata_sandi: String,
    val konfirmasi_kata_sandi: String
)
