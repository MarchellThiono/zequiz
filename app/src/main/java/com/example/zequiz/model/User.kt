package com.example.zequiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id : Int,
    val username : String,
    val password : String,
    val role : String
) : Parcelable
