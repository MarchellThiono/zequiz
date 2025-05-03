package com.example.zequiz.model

import com.google.gson.annotations.SerializedName

data class TampilTopikResItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("kelas")
	val kelas: TampilTopikKelas? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class TampilTopikRes(

	@field:SerializedName("tampikTopikRes")
	val tampilTopikRes: List<TampilTopikResItem?>? = null
)

data class TampilTopikKelas(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
