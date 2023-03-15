package com.trdz.astro_days.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trdz.astro_days.model.Repository
import com.trdz.astro_days.model.RepositoryExecutor

class ViewModelFactories(
	private val dataPodLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPomLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPoeLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val messageLive: SingleLiveData<StatusMessage> = SingleLiveData(),
	private val repository: Repository = RepositoryExecutor(),
): ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
		MainViewModel::class.java -> MainViewModel(dataPodLive,dataPomLive,dataPoeLive,messageLive,repository)
		else -> throw IllegalArgumentException("Unknown ViewModel class")
	} as T

}