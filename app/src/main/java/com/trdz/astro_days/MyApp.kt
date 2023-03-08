package com.trdz.astro_days

import android.app.Application
import com.google.gson.GsonBuilder
import com.trdz.astro_days.di.moduleDataK
import com.trdz.astro_days.di.moduleMainK
import com.trdz.astro_days.di.moduleRepositoryK
import com.trdz.astro_days.di.moduleViewModelK
import com.trdz.astro_days.model.server_mrp.ServerRetrofitMrpCustomApi
import com.trdz.astro_days.model.server_pod.ServerRetrofitPodApi
import com.trdz.astro_days.model.server_pod.ServerRetrofitPodCustomApi
import com.trdz.astro_days.utility.DOMAIN
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp: Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@MyApp)
			modules(listOf(moduleMainK, moduleRepositoryK, moduleViewModelK, moduleDataK))
		}
	}

	companion object {
		private var retrofitPod: ServerRetrofitPodApi? = null
		private var retrofitPodCustom: ServerRetrofitPodCustomApi? = null
		private var retrofitMrp: ServerRetrofitMrpCustomApi? = null

		private fun createRetrofit() {
			retrofitPod = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitPodApi::class.java)
		}

		fun getRetrofit(): ServerRetrofitPodApi {
			if (retrofitPod == null) createRetrofit()
			return retrofitPod!!
		}
		private fun createRetrofitCustom() {
			retrofitPodCustom = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitPodCustomApi::class.java)
		}

		fun getRetrofitCustom(): ServerRetrofitPodCustomApi {
			if (retrofitPodCustom == null) createRetrofitCustom()
			return retrofitPodCustom!!
		}

		private fun createRetrofitMrp() {
			retrofitMrp = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitMrpCustomApi::class.java)
		}

		fun getRetrofitMrp(): ServerRetrofitMrpCustomApi {
			if (retrofitMrp == null) createRetrofitMrp()
			return retrofitMrp!!
		}
	}
}