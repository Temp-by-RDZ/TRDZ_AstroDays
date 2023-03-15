package com.trdz.astro_days.di

import com.google.gson.GsonBuilder
import com.trdz.astro_days.model.server_mrp.ServerRetrofitMrpCustomApi
import com.trdz.astro_days.model.server_pod.ServerRetrofitPodApi
import com.trdz.astro_days.model.server_pod.ServerRetrofitPodCustomApi
import com.trdz.astro_days.utility.DOMAIN
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moduleDataK = module {
	single<ServerRetrofitPodApi>() {
		Retrofit.Builder().apply {
			baseUrl(DOMAIN)
			addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
		}.build().create(ServerRetrofitPodApi::class.java)
	}

	single<ServerRetrofitPodCustomApi>() {
		Retrofit.Builder().apply {
			baseUrl(DOMAIN)
			addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
		}.build().create(ServerRetrofitPodCustomApi::class.java)
	}

	single<ServerRetrofitMrpCustomApi>() {
		Retrofit.Builder().apply {
			baseUrl(DOMAIN)
			addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
		}.build().create(ServerRetrofitMrpCustomApi::class.java)
	}

}


