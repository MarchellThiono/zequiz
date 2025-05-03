package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class BuatTopikRes(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: TopikKelas? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class TopikKelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
