package com.trdz.astro_days.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.astro_days.model.RepositoryExecutor
import com.trdz.astro_days.model.ServersResult
import com.trdz.astro_days.model.*
import com.trdz.astro_days.utility.PREFIX_EPC
import com.trdz.astro_days.utility.PREFIX_MRP
import com.trdz.astro_days.utility.PREFIX_POD
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
	private val dataPodLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPomLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPoeLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val messageLive: SingleLiveData<StatusMessage> = SingleLiveData(),
	private val repository: Repository = RepositoryExecutor(),
): ViewModel(), ServerResponse {

	private var page: Int = 1
	var setChange = 0

	fun changePage(goTo: Int) {
		if (page < 0) {
			page = 0
			return
		}
		else {
			page = goTo
			pageObserve()
		}
	}

	private fun pageObserve() {
		when (page) {
			1 -> messageLive.postValue(StatusMessage.SetupComplete)
			2 -> page = 1
		}
	}

	fun getPoeData(): LiveData<StatusProcess> = dataPoeLive
	fun getPomData(): LiveData<StatusProcess> = dataPomLive
	fun getPodData(): LiveData<StatusProcess> = dataPodLive
	fun getMessage(): LiveData<StatusMessage> = messageLive

	fun initialize(prefix: String) {
		messageLive.postValue(StatusMessage.Success)
		if (setChange!=0) when (prefix) {
			PREFIX_POD -> startPod(getData(setChange))
			else -> analyze(prefix, setChange-1)
		}
		else when (prefix) {
			PREFIX_POD -> startPod()
			else -> analyze(prefix)
		}
	}

	private fun getData(change: Int): String {
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, change)
		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
		return dateFormat.format(calendar.time)
	}
	fun needSave(prefix: String) {
		when (prefix) {
			PREFIX_POD -> dataPodLive.postValue(StatusProcess.Saving((getData(0))))
			PREFIX_MRP -> dataPomLive.postValue(StatusProcess.Saving((getData(0))))
			PREFIX_EPC -> dataPoeLive.postValue(StatusProcess.Saving((getData(0))))
		}

	}

	fun analyze(prefix: String, change: Int = setChange) {
		when (prefix) {
			PREFIX_POD -> startPod(getData(change))
			PREFIX_MRP -> startMrp(getData(change-1))
			PREFIX_EPC -> startEpc(getData(change-1))
		}
	}

	private fun startPod(date: String? = null) {
		dataPodLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, PREFIX_POD, date)
	}

	private fun startMrp(date: String? = null) {
		dataPomLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, PREFIX_MRP, date)
	}

	private fun startEpc(date: String? = null) {
		dataPoeLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, PREFIX_EPC, date)
	}

	override fun success(prefix: String, data: ServersResult) {
		Log.d("@@@", "Mod - get success $prefix answer")
		when (prefix) {
			PREFIX_POD -> {
				if (data.type == "video") {
					dataPodLive.postValue(StatusProcess.Video(data))
					messageLive.postValue(StatusMessage.VideoError)
				}
				else {
					dataPodLive.postValue(StatusProcess.Success(data))
				}
			}
			PREFIX_MRP -> {
				dataPomLive.postValue(StatusProcess.Success(data))
			}
			PREFIX_EPC -> {
				dataPoeLive.postValue(StatusProcess.Success(data))
			}
		}
	}

	override fun fail(prefix: String, code: Int) {
		Log.d("@@@", "Mod - request $prefix failed $code")
		when (prefix) {
			PREFIX_POD -> dataPodLive.postValue(StatusProcess.Error(code, IllegalAccessError()))
			PREFIX_MRP -> dataPomLive.postValue(StatusProcess.Error(code, IllegalAccessError()))
			PREFIX_EPC -> dataPoeLive.postValue(StatusProcess.Error(code, IllegalAccessError()))
		}
	}
}