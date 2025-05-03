package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class ListKuisRes(

	@field:SerializedName("ListKuisRes")
	val listKuisRes: List<ListKuisResItem?>? = null
)

data class ListKuisResItem(

	@field:SerializedName("timer")
	val timer: Int? = null,

	@field:SerializedName("namaKuis")
	val namaKuis: String? = null,

	@field:SerializedName("namaTopik")
	val namaTopik: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("jumlahSoal")
	val jumlahSoal: Int? = null
)
