package com.example.zequiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SkorData(
    val id: Long,
    val kuisId: Long,
    val siswaId: Long,
    val skor: Int,
    val namaSiswa: String,
    val judulKuis: String
): Parcelable
