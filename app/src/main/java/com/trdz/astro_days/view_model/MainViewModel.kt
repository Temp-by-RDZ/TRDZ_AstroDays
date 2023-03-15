package com.trdz.astro_days.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.astro_days.model.RepositoryExecutor
import com.trdz.astro_days.model.RequestResult
import com.trdz.astro_days.model.*
import com.trdz.astro_days.utility.PREFIX_EPC
import com.trdz.astro_days.utility.PREFIX_MRP
import com.trdz.astro_days.utility.PREFIX_POD
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

/** Главная VM для сегмента Picture */
class MainViewModel(
	private val dataPodLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPomLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPoeLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val messageLive: SingleLiveData<StatusMessage> = SingleLiveData(),
	private val repository: Repository = RepositoryExecutor(),
): ViewModel(), ServerResponse {

	private var page: Int = 1
	var setChange = 0

	/** Переключение фаз работы VM в зависимости от текущего экрана */
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

	/** Подготовка запроса*/
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

	/** Работа с датой для запроса */
	private fun getData(change: Int): String {
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, change)
		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
		return dateFormat.format(calendar.time)
	}

	/** Принятие сигнала на подготовку сохранения картинки с экрана на диск */
	fun needSave(prefix: String) {
		when (prefix) {
			PREFIX_POD -> dataPodLive.postValue(StatusProcess.Saving((getData(0))))
			PREFIX_MRP -> dataPomLive.postValue(StatusProcess.Saving((getData(0))))
			PREFIX_EPC -> dataPoeLive.postValue(StatusProcess.Saving((getData(0))))
		}

	}

	/** Подготовка запроса по настраиваемому дню месяца */
	fun analyze(prefix: String, change: Int = setChange) {
		when (prefix) {
			PREFIX_POD -> startPod(getData(change))
			PREFIX_MRP -> startMrp(getData(change-1))
			PREFIX_EPC -> startEpc(getData(change-1))
		}
	}

	/** Подготовка к запросу NASA PictureOfTheDay */
	private fun startPod(date: String? = null) {
		dataPodLive.postValue(StatusProcess.Load)
		request(PREFIX_POD, date)
	}

	/** Подготовка к запросу NASA MarsRoverPicture */
	private fun startMrp(date: String? = null) {
		dataPomLive.postValue(StatusProcess.Load)
		request(PREFIX_MRP, date)
	}

	/** Подготовка к запросу NASA EpicPicture */
	private fun startEpc(date: String? = null) {
		dataPoeLive.postValue(StatusProcess.Load)
		request(PREFIX_EPC, date)
	}

	/** Выполнение запроса */
	private fun request(prefix: String, date: String?) {
		repository.connection(prefix, date)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{
					if (it.code in 200..299) {
						success(it)
					}
					else {
						fail(it.prefix,it.code,Throwable(it.description))
					}
				},
				{ exception -> fail(prefix,-4, exception) })
	}

	/** Реакция MV на успех запроса */
	override fun success(data: RequestResult) {
		Log.d("@@@", "Mod - get success ${data.prefix} answer")
		when (data.prefix) {
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

	/** Реакция MV на ошибку запроса */
	override fun fail(prefix: String, code: Int, throwable: Throwable?) {
		Log.d("@@@", "Mod - request $prefix failed $code")
		val message = throwable ?: Throwable("Unspecified Error")
		when (prefix) {
			PREFIX_POD -> dataPodLive.postValue(StatusProcess.Error(code, message))
			PREFIX_MRP -> dataPomLive.postValue(StatusProcess.Error(code, message))
			PREFIX_EPC -> dataPoeLive.postValue(StatusProcess.Error(code, message))
		}
	}
}