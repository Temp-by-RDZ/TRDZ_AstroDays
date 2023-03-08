package com.trdz.astro_days.di

import com.trdz.astro_days.R
import com.trdz.astro_days.view.Navigation
import org.koin.dsl.module

val moduleMainK = module {
	single<Navigation>() { Navigation(R.id.container_fragment_base) }
}


