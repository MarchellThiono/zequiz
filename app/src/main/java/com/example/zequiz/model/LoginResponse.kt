package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("kelas")
	val kelas: Int? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
