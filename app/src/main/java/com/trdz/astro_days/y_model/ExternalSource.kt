package com.trdz.astro_days.y_model

interface ExternalSource {
	fun load(date: String?): ServersResult
}