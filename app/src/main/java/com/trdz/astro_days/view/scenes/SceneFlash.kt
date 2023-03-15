package com.trdz.astro_days.view.scenes

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.trdz.astro_days.R

/** Эффект мерцания экрана */
class SceneFlash: Fragment() {

	//region Elements
	private var durationBoth = 200L
	//endregion

	//region Base realization
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.scene_flash, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initialize()
	}

	//endregion

	//region Main functional
	/** Задание начального исполнения основного функционала*/
	private fun initialize() {
		shine()
	}

	/** Засвет экрана */
	private fun shine() {
		requireActivity().findViewById<FrameLayout>(R.id.white_screen).animate().apply {
			alpha(0.8f)
			duration = durationBoth
			withEndAction { blank() }
			start()
		}
	}
	/** Возврат к норме */
	private fun blank() {
		requireActivity().findViewById<FrameLayout>(R.id.white_screen).animate().apply {
			alpha(0.0f)
			duration = durationBoth
			withEndAction { requireActivity().supportFragmentManager.beginTransaction().detach(this@SceneFlash).commit() }
			start()
		}
	}
	//endregion

	companion object {
		fun newInstance() = SceneFlash()
	}


}

