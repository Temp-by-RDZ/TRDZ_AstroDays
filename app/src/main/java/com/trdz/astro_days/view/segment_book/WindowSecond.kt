package com.trdz.astro_days.view.segment_book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.astro_days.databinding.FragmentWindowSecondBinding

class WindowSecond: Fragment() {

	//region Elements
	private var _binding: FragmentWindowSecondBinding? = null
	private val binding get() = _binding!!

	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowSecondBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initialize()
	}

	//endregion

	//region Main functional
	/** Задание начального исполнения основного функционала*/
	private fun initialize() {
		buttonBinds()
	}

	private fun buttonBinds() {
	}

	//endregion

	companion object {
		fun newInstance() = WindowSecond()
	}
}