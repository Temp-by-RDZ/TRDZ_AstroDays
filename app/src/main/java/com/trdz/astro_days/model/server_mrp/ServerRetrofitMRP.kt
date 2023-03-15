package com.trdz.astro_days.model.server_mrp

import android.util.Log
import com.trdz.astro_days.BuildConfig
import com.trdz.astro_days.model.ExternalSource
import com.trdz.astro_days.model.RequestResult
import com.trdz.astro_days.model.server_mrp.dto.ResponseDataMRP
import org.koin.java.KoinJavaComponent
import retrofit2.Response

/** Получение данных с Api Mars Rovers Photos */
class ServerRetrofitMRP: ExternalSource {

	private val retrofit: ServerRetrofitMrpCustomApi by KoinJavaComponent.inject(ServerRetrofitMrpCustomApi::class.java)

	override fun load(date: String?): RequestResult {

		return try {
			val response = retrofit.getResponse(date!!,BuildConfig.NASA_API_KEY).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail(Ignored)
		}
	}

	private fun responseFormation(response: Response<ResponseDataMRP>) : RequestResult {
		return if (response.isSuccessful) response.body()!!.run {
			if (photos.isEmpty()) return@run RequestResult(-2)
			val picture = photos.random()
			RequestResult(response.code(), picture.rover.name, picture.rover.status, picture.img_src, picture.camera.name)
		}
		else RequestResult(response.code())
	}

	private fun responseFail(exception: Exception) : RequestResult {
		Log.d("@@@", "Ser - MRP Connection Error")
		val message = exception.message ?: exception.toString()
		return RequestResult(-1,"Error",message)
	}
}
