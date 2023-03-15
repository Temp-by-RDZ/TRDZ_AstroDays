package com.trdz.astro_days.model.server_pod

import android.util.Log
import com.trdz.astro_days.BuildConfig
import com.trdz.astro_days.model.ExternalSource
import com.trdz.astro_days.model.RequestResult
import com.trdz.astro_days.model.server_pod.dto.ResponseDataPOD
import org.koin.java.KoinJavaComponent
import retrofit2.Response

/** Получение данных с Api Picture of my Day
 */
class ServerRetrofitPOD: ExternalSource {

	private val retrofit: ServerRetrofitPodApi by KoinJavaComponent.inject(ServerRetrofitPodApi::class.java)
	private val retrofitCustom: ServerRetrofitPodCustomApi by KoinJavaComponent.inject(ServerRetrofitPodCustomApi::class.java)

	override fun load(date: String?): RequestResult {
		if (date!=null) return loadCustom(date)
		return try {
			val response = retrofit.getResponse(BuildConfig.NASA_API_KEY).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail(Ignored)
		}
	}

	private fun loadCustom(date: String): RequestResult {
		return try {
			val response = retrofitCustom.getResponse(BuildConfig.NASA_API_KEY,date).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail(Ignored)
		}
	}

	private fun responseFormation(response: Response<ResponseDataPOD>) : RequestResult {
		return if (response.isSuccessful) response.body()!!.run {
			RequestResult(response.code(), title, explanation, url, mediaType)
		}
		else RequestResult(response.code())
	}

	private fun responseFail(exception: Exception) : RequestResult {
		Log.d("@@@", "Ser - POD Connection Error "+exception.message)
		val message = exception.message ?: exception.toString()
		return RequestResult(-1,"Error",message)
	}
}
