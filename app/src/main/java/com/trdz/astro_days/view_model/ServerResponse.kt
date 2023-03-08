package com.trdz.astro_days.view_model

import com.trdz.astro_days.model.ServersResult

interface ServerResponse {
	fun success(prefix: String, data: ServersResult)
	fun fail(prefix: String, code: Int)
}