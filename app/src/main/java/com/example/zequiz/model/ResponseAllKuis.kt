package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class ResponseAllKuis(

	@field:SerializedName("ResponseAllKuis")
	val responseAllKuis: List<ResponseAllKuisItem?>? = null
)

data class Topik(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: Kelas? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class ResponseAllKuisItem(

	@field:SerializedName("timer")
	val timer: Int? = null,

	@field:SerializedName("topik")
	val topik: Topik? = null,

	@field:SerializedName("guru")
	val guru: Guru? = null,

	@field:SerializedName("kelas")
	val kelas: Kelas? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("jumlahSoal")
	val jumlahSoal: Int? = null
)

data class Kelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Guru(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("kelas")
	val kelas: Kelas? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("username")
	val username: String? = null
)
