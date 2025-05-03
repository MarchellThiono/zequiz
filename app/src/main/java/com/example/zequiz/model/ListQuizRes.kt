package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class ListQuizRes(

	@field:SerializedName("ListQuizRes")
	val listQuizRes: List<ListQuizResItem?>? = null
)

data class ListQuizResItem(

	@field:SerializedName("opsiA")
	val opsiA: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("opsiB")
	val opsiB: String? = null,

	@field:SerializedName("opsiC")
	val opsiC: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("opsiD")
	val opsiD: String? = null,

	@field:SerializedName("pertanyaan")
	val pertanyaan: String? = null
)
