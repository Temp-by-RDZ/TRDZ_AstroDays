package com.trdz.astro_days.model.server_mrp

import android.util.Log
import com.trdz.astro_days.BuildConfig
import com.trdz.astro_days.MyApp
import com.trdz.astro_days.model.ExternalSource
import com.trdz.astro_days.model.ServersResult
import com.trdz.astro_days.model.server_mrp.dto.ResponseDataMRP
import com.trdz.astro_days.model.server_pod.ServerRetrofitPodApi
import org.koin.java.KoinJavaComponent
import retrofit2.Response

/** Получение данных с Api Mars Rovers Photos */
class ServerRetrofitMRP: ExternalSource {

	private val retrofit: ServerRetrofitMrpCustomApi by KoinJavaComponent.inject(ServerRetrofitMrpCustomApi::class.java)

	override fun load(date: String?): ServersResult {

		return try {
			val response = retrofit.getResponse(date!!,BuildConfig.NASA_API_KEY).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail()
		}
	}

	private fun responseFormation(response: Response<ResponseDataMRP>) : ServersResult {
		return if (response.isSuccessful) response.body()!!.run {
			if (photos.isEmpty()) return@run ServersResult(-2)
			val picture = photos.random()
			ServersResult(response.code(), picture.rover.name, picture.rover.status, picture.img_src, picture.camera.name)
		}
		else ServersResult(response.code())
	}

	private fun responseFail() : ServersResult {
		Log.d("@@@", "Ser - MRP Connection Error")
		return ServersResult(-1)
	}
}
