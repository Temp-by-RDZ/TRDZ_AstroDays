package com.trdz.astro_days.model.server_pod

import android.util.Log
import com.trdz.astro_days.BuildConfig
import com.trdz.astro_days.MyApp
import com.trdz.astro_days.model.ExternalSource
import com.trdz.astro_days.model.ServersResult
import com.trdz.astro_days.model.server_pod.dto.ResponseDataPOD
import org.koin.java.KoinJavaComponent
import retrofit2.Response

class ServerRetrofitPOD: ExternalSource {

	private val retrofit: ServerRetrofitPodApi by KoinJavaComponent.inject(ServerRetrofitPodApi::class.java)
	private val retrofitCustom: ServerRetrofitPodCustomApi by KoinJavaComponent.inject(ServerRetrofitPodCustomApi::class.java)

	override fun load(date: String?): ServersResult {
		if (date!=null) return loadCustom(date)
		return try {
			val response = retrofit.getResponse(BuildConfig.NASA_API_KEY).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail(Ignored)
		}
	}

	private fun loadCustom(date: String): ServersResult {
		return try {
			val response = retrofitCustom.getResponse(BuildConfig.NASA_API_KEY,date).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail(Ignored)
		}
	}

	private fun responseFormation(response: Response<ResponseDataPOD>) : ServersResult {
		return if (response.isSuccessful) response.body()!!.run {
			ServersResult(response.code(), title, explanation, url, mediaType)
		}
		else ServersResult(response.code())
	}

	private fun responseFail(Ignored: Exception) : ServersResult {
		Log.d("@@@", "Ser - POD Connection Error "+Ignored.message)
		return ServersResult(-1)
	}
}
