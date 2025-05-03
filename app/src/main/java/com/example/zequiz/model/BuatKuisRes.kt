package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class BuatKuisRes(

	@field:SerializedName("topik")
	val topik: Topik? = null,

	@field:SerializedName("timer")
	val timer: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: Kelas? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jumlahSoal")
	val jumlahSoal: Int? = null
)

data class Topik(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Kelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
