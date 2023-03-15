package com.trdz.astro_days.model

import android.util.Log
import com.trdz.astro_days.view_model.ServerResponse
import com.trdz.astro_days.model.server_epc.ServerReceiverEPC
import com.trdz.astro_days.model.server_mrp.ServerRetrofitMRP
import com.trdz.astro_days.model.server_pod.ServerRetrofitPOD
import com.trdz.astro_days.utility.PREFIX_EPC
import com.trdz.astro_days.utility.PREFIX_MRP
import com.trdz.astro_days.utility.PREFIX_POD
import io.reactivex.rxjava3.core.Single

class RepositoryExecutor: Repository {
	/** Отправка запроса NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
	override fun connection(prefix: String, date: String?): Single<RequestResult> {
		Log.d("@@@", "Rep - start connection $prefix on date: $date")
		lateinit var externalSource: ExternalSource
		when (prefix) {
			PREFIX_POD -> externalSource = ServerRetrofitPOD()
			PREFIX_MRP -> externalSource = ServerRetrofitMRP()
			PREFIX_EPC -> externalSource = ServerReceiverEPC()
		}
		return Single.create{
			val data = externalSource.load(date).copy(prefix = prefix)
			it.onSuccess(data)
		}
	}

}
