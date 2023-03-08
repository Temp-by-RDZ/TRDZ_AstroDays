package com.trdz.astro_days.view.segment_book

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.astro_days.databinding.FragmentWindowThirdBinding

class WindowThird: Fragment() {

	//region Elements
	private var _binding: FragmentWindowThirdBinding? = null
	private val binding get() = _binding!!
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowThirdBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
	}

	private fun initialize() {
	}

	//endregion


	companion object {
		fun newInstance() = WindowThird()
	}


}

