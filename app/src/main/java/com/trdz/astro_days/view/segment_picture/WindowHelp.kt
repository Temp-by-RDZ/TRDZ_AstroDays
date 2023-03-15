package com.trdz.astro_days.view.segment_picture

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.trdz.astro_days.databinding.FragmentWindowHelpBinding
import com.trdz.astro_days.view.Navigation
import com.trdz.astro_days.view_model.MainViewModel
import com.trdz.astro_days.view_model.ViewModelFactories
import org.koin.android.ext.android.inject

class WindowHelp: Fragment() {

	//region Injected
	private val factory: ViewModelFactories by inject()

	private val viewModel: MainViewModel by viewModels {
		factory
	}

	//endregion

	//region Elements
	private var _binding: FragmentWindowHelpBinding? = null
	private val binding get() = _binding!!

	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowHelpBinding.inflate(inflater, container, false)
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
		with(binding) {
			back.setOnClickListener{ closeHelp() }
			ok.setOnClickListener{ closeHelp() }
		}
	}

	private fun closeHelp() {
		viewModel.changePage(1)
		requireActivity().supportFragmentManager.popBackStack()
	}

	//endregion

}