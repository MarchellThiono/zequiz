package com.example.zequiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class JawabanSiswa(
    val idSiswa: Int,
    val idSoal: Int,
    val jawabanDipilih : String
) : Parcelable
