package com.trdz.astro_days.model.server_epc

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.trdz.astro_days.BuildConfig
import com.trdz.astro_days.model.ExternalSource
import com.trdz.astro_days.model.RequestResult
import com.trdz.astro_days.model.server_epc.dto.ResponseDataEPC
import com.trdz.astro_days.utility.DOMAIN
import com.trdz.astro_days.utility.PACKAGE_EPA
import com.trdz.astro_days.utility.PACKAGE_EPC
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.text.StringBuilder

/** Получение данных с Api Earth Pictures */
class ServerReceiverEPC(): ExternalSource {

	override fun load(date: String?): RequestResult {
		var responseCode = -1

		val uri = URL(StringBuilder("").apply {
			append(DOMAIN)
			append(PACKAGE_EPC)
			append(date)
			append("?api_key=")
			append(BuildConfig.NASA_API_KEY)
		}.toString())

		val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply {
			connectTimeout = 1000
			readTimeout = 1000
		}

		try { responseCode = urlConnection.responseCode }
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser- Connection Error")
			return RequestResult(responseCode)
		}

		try {
			if (responseCode in 1..299) {

				val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
				val responseData: ResponseDataEPC = Gson().fromJson(buffer, ResponseDataEPC::class.java)
				if (responseData.isEmpty()) return RequestResult(-2)
				val result = responseData.random()
				val imageEPA = StringBuilder().apply {
					append(DOMAIN)
					append(PACKAGE_EPA)
					append(date!!.replace("-","/"))
					append("/png/")
					append(result.image)
					append(".png?api_key=")
					append(BuildConfig.NASA_API_KEY)
				}
				return RequestResult(responseCode, "Earth"+result.centroid_coordinates,result.caption, imageEPA.toString(),"EPA_Image")
			}
		}
		catch (Ignored: JsonSyntaxException) {
			return RequestResult(responseCode)
		}
		finally {
			urlConnection.disconnect()
		}
		return RequestResult(responseCode)

	}
}