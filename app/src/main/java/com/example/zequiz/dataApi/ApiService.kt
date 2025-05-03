package com.example.zequiz.dataApi

import com.example.zequiz.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // =================== AUTH ===================
    @POST("auth/register")
    fun registerUser(@Body request: UserRegisterReq): Call<RegisterResponse>

    @POST("auth/login")
    fun loginUser(@Body request: UserLoginReq): Call<LoginResponse>

    //     =================== Topik ===================
    @POST("zequiz/topik/buat")
    fun buatTopik(@Body request: BuatTopikReq):Call<BuatTopikRes>

    @GET("zequiz/topik/my")
    fun ambilTopik( ): Call<TampilTopikResItem>

    //     =================== Kuis ===================
    @GET("kuis/kelas/{kelasId}")
    fun getAllKuis(@Path("kelasId") kelasId: Long): Call<ListKuisRes>

    @POST(" zequiz/kuis/buat?userId=1&topikId=1")
    fun buatKuis(@Query ("userId") userId: Int, @Query("topikId") topikId: Int): Call<BuatKuisRes>

    //     =================== SOAL ===================

    @GET("zequiz/kuis/{kuisId}/soal")
    fun getSoalByTopik(@Path("kuisId")topikId: Long): Call<SoalResItem>

    @GET(" zequiz/soal/topik/{topikId}")
    fun getSoalByTopik(@Path("topikId") topikId: Int): Call<List<SoalRes>>

    @POST("zequiz/soal/tambah")
    fun tambahSoalByTopik(): Call<TambahSoalRes>

    @DELETE("zequiz/soal/hapus/{soalId}")
    fun hapusSoal(@Path("SoalId") soalId: Long): Call<HapusSoalRes>

    // =================== SKOR ===================
    @GET(" zequiz/skor/kuis/{kuisId}")
    fun checkScor(@Path("kuisId") kuisId: Long): Call<CheckScorRes>

    @POST("zequiz/skor/hitung")
    fun hitungSkor(
        @Body hitungScorReq: HitungScorReq,
        @Query("kuisId") kuisId: Int
    ): Call<CalculateQuizRes>


//    @GET("zequiz/skor/belum/{kuisId}")
//    fun getSiswaBelum(@Path("kuisId") kuisId: Long): Call<List<SiswaBelum>>
//
//    @GET("zequiz/skor/sudah/{kuisId}")
//    fun getSiswaSudah(@Path("kuisId") kuisId: Long): Call<List<SiswaSudah>>

    companion object {
        fun getInstance(): ApiService {
            return ApiClient.instance
        }
    }
}
