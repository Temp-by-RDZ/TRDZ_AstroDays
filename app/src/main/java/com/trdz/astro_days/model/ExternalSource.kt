package com.trdz.astro_days.model

/** Интерфейс для Источников Данных */
interface ExternalSource {
	fun load(date: String?): ServersResult
}