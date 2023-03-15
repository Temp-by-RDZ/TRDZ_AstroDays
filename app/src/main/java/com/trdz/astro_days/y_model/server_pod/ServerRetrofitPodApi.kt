package com.trdz.astro_days.y_model.server_pod

import com.trdz.astro_days.y_model.server_pod.dto.ResponseDataPOD
import com.trdz.astro_days.z_utility.PACKAGE_POD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerRetrofitPodApi {
	@GET(PACKAGE_POD)
	fun getResponse(
		@Query("api_key") apiKey:String
	): Call<ResponseDataPOD>
}
interface ServerRetrofitPodCustomApi {
	@GET(PACKAGE_POD)
	fun getResponse(
		@Query("api_key") apiKey:String,
		@Query("date") date:String
	): Call<ResponseDataPOD>
}