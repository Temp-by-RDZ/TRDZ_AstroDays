package com.trdz.astro_days.x_view_model

import com.trdz.astro_days.y_model.ServersResult

interface ServerResponse {
	fun success(prefix: String, data: ServersResult)
	fun fail(prefix: String, code: Int)
}