package com.trdz.astro_days.view_model

import com.trdz.astro_days.model.RequestResult

/** Ответы VM для фрагментов: */
sealed class StatusMessage {
	object Success: StatusMessage()
	object VideoError: StatusMessage()
	object SetupComplete: StatusMessage()
}

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Saving(val data: String) : StatusProcess()
	data class Success(val data: RequestResult) : StatusProcess()
	data class Video(val data: RequestResult) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
}