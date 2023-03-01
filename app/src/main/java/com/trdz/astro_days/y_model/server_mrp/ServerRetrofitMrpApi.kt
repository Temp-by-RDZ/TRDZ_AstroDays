package com.trdz.astro_days.y_model.server_mrp

import com.trdz.astro_days.y_model.server_mrp.dto.ResponseDataMRP
import com.trdz.astro_days.z_utility.PACKAGE_MRP
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerRetrofitMrpCustomApi {
	@GET(PACKAGE_MRP)
	fun getResponse(
		@Query("earth_date") date:String,
		@Query("api_key") apiKey:String,
	): Call<ResponseDataMRP>
}