package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class TambahSoalRes(

	@field:SerializedName("jawabanBenar")
	val jawabanBenar: String? = null,

	@field:SerializedName("topik")
	val topik: Topik? = null,

	@field:SerializedName("opsiA")
	val opsiA: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("opsiB")
	val opsiB: String? = null,

	@field:SerializedName("opsiC")
	val opsiC: String? = null,

	@field:SerializedName("gambar")
	val gambar: Any? = null,

	@field:SerializedName("opsiD")
	val opsiD: String? = null,

	@field:SerializedName("pertanyaan")
	val pertanyaan: String? = null
)

data class TambahSoalTopik(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: Kelas? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class TambahSoalKelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
