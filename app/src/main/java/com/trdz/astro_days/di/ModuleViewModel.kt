package com.trdz.astro_days.di

import com.trdz.astro_days.model.Repository
import com.trdz.astro_days.model.RepositoryExecutor
import com.trdz.astro_days.view_model.SingleLiveData
import com.trdz.astro_days.view_model.StatusProcess
import com.trdz.astro_days.view_model.ViewModelFactories
import org.koin.dsl.module

val moduleViewModelK = module {
	single<Repository>() {
		RepositoryExecutor()
	}
	factory<SingleLiveData<StatusProcess>>() { SingleLiveData() }
	single<ViewModelFactories>() {
		ViewModelFactories(
			dataPodLive = get(),
			dataPomLive = get(),
			dataPoeLive = get(),
			messageLive = get(),
			repository = get(),
		)
	}
}


