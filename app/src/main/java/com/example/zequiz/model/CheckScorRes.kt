package com.example.zequiz.model

data class CheckScorRes(
	val kuis: Kuis? = null,
	val siswaId: Int? = null,
	val id: Int? = null,
	val skor: Int? = null
)

data class Topik(
	val nama: String? = null,
	val id: Int? = null
)

data class Kelas(
	val nama: String? = null,
	val id: Int? = null
)

data class Kuis(
	val nama: String? = null,
	val timer: Int? = null,
	val topik: Topik? = null,
	val kelas: Kelas? = null,
	val id: Int? = null,
	val tanggal: String? = null,
	val jumlahSoal: Int? = null
)

