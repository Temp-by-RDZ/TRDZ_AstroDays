package com.trdz.astro_days

import android.app.Application
import com.trdz.astro_days.di.moduleDataK
import com.trdz.astro_days.di.moduleMainK
import com.trdz.astro_days.di.moduleRepositoryK
import com.trdz.astro_days.di.moduleViewModelK
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@MyApp)
			modules(listOf(moduleMainK, moduleRepositoryK, moduleViewModelK, moduleDataK))
		}
	}
}