package com.trdz.astro_days.model

interface ExternalSource {
	fun load(date: String?): ServersResult
}