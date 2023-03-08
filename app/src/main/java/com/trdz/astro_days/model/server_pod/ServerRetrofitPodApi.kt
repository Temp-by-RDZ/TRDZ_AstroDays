package com.trdz.astro_days.model.server_pod

import com.trdz.astro_days.model.server_pod.dto.ResponseDataPOD
import com.trdz.astro_days.utility.PACKAGE_POD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/** Запрос одиночной картинки с Picture of My Day */
interface ServerRetrofitPodApi {
	@GET(PACKAGE_POD)
	fun getResponse(
		@Query("api_key") apiKey:String
	): Call<ResponseDataPOD>
}
/** Запрос картинки по дате публикаүии с Picture of My Day */
interface ServerRetrofitPodCustomApi {
	@GET(PACKAGE_POD)
	fun getResponse(
		@Query("api_key") apiKey:String,
		@Query("date") date:String
	): Call<ResponseDataPOD>
}