package com.trdz.astro_days.w_view.segment_picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.astro_days.databinding.DiologWikipediaBinding
import com.trdz.astro_days.z_utility.Wikipedia
import com.trdz.astro_days.z_utility.hideKeyboard

class WindowSearch: Fragment() {

	//region Elements
	private var _binding: DiologWikipediaBinding? = null
	private val binding get() = _binding!!
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = DiologWikipediaBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		binding.freeSpace.setOnClickListener {
			hideKeyboard()
			requireActivity().supportFragmentManager.popBackStack() }
		binding.inputSearchBlock.setEndIconOnClickListener {
			startActivity(Intent(Intent.ACTION_VIEW).apply {
				data = Uri.parse("$Wikipedia${binding.inputSearch.text.toString()}")
			})
		}
	}

	//endregion

}