package com.trdz.astro_days.view_model

import com.trdz.astro_days.model.RequestResult
import java.lang.Exception

/** Ожидаемые действия VM на обращения */
interface ServerResponse {
	fun success(data: RequestResult)
	fun fail(prefix: String, code: Int, throwable: Throwable?)
}