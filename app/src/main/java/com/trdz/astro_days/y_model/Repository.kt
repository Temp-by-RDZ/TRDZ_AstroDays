package com.trdz.astro_days.y_model

import com.trdz.astro_days.x_view_model.ServerResponse

interface Repository {
	fun connection(serverListener: ServerResponse, prefix: String, date: String?)
}