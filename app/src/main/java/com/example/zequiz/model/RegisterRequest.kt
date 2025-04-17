package com.example.zequiz.model

data class RegisterRequest(
    val username:String,
    val kelas : String,
    val kataSandi: String,
    val konfirmasiKataSandi :String
)
