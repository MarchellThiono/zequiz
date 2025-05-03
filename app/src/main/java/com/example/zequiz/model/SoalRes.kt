package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class SoalRes(

	@field:SerializedName("SoalRes")
	val soalRes: List<SoalResItem?>? = null
)

data class SoalResItem(

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

data class Kelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Topik(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: Kelas? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
