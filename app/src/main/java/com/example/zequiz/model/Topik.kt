package com.example.zequiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Topik(
    val id : Int,
    val topik : String,
    val jumlah : String,
    val status : String,
    val tanggal : String
) : Parcelable
