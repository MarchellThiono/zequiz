package com.example.zequiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Soal(
    val id : Int,
    val topikId : Int,
    val gambar : String,
    val pertanyaan : String,
    val pilihanA : String,
    val pilihanB :String,
    val pilihanC : String,
    val pilihanD : String,
    val jawabanBenar : String
) : Parcelable
