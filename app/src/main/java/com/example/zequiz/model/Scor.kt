package com.example.zequiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Scor(
    val idSiswa: Int,
    val scorTotal : Int,
    val jumlahBenar : Int,
    val jumlahSoal : Int
) : Parcelable
