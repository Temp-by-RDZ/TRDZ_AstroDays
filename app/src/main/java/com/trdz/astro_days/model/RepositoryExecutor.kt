package com.trdz.astro_days.model

import android.util.Log
import com.trdz.astro_days.view_model.ServerResponse
import com.trdz.astro_days.model.server_epc.ServerReceiverEPC
import com.trdz.astro_days.model.server_mrp.ServerRetrofitMRP
import com.trdz.astro_days.model.server_pod.ServerRetrofitPOD
import com.trdz.astro_days.utility.PREFIX_EPC
import com.trdz.astro_days.utility.PREFIX_MRP
import com.trdz.astro_days.utility.PREFIX_POD

class RepositoryExecutor: Repository {

	override fun connection(serverListener: ServerResponse, prefix: String, date: String?) {
		Log.d("@@@", "Rep - start connection $prefix on date: $date")
		lateinit var externalSource: ExternalSource
		when (prefix) {
			PREFIX_POD -> externalSource = ServerRetrofitPOD()
			PREFIX_MRP -> externalSource = ServerRetrofitMRP()
			PREFIX_EPC -> externalSource = ServerReceiverEPC()
		}
		Thread {
			val result = externalSource.load(date)
			if (result.code in 200..299 ) serverListener.success(prefix,result)
			else serverListener.fail(prefix, result.code)
		}.start()
	}

}
