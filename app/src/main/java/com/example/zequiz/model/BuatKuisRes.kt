package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class BuatKuisRes(

	@field:SerializedName("topik")
	val topik: KuisTopik? = null,

	@field:SerializedName("timer")
	val timer: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: KuisKelas? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("jumlahSoal")
	val jumlahSoal: Int? = null
)

data class KuisTopik(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class KuisKelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
