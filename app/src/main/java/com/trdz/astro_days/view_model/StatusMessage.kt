package com.trdz.astro_days.view_model

sealed class StatusMessage {
	object Success: StatusMessage()
	object VideoError: StatusMessage()
	object SetupComplete: StatusMessage()
}