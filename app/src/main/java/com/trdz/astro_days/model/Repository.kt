package com.trdz.astro_days.model

import com.trdz.astro_days.view_model.ServerResponse

interface Repository {
	fun connection(serverListener: ServerResponse, prefix: String, date: String?)
}