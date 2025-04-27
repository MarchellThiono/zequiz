package com.example.zequiz.model


data class Kuis(
    val id: Long,
    val timer: Int,
    val jumlahSoal: Int,
    val tanggal: String,
    val topikNama: String,
    val guruNama: String,
    val topikId: Int,
    val kelasNama: String
)

