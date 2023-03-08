package com.trdz.astro_days.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.astro_days.R
import com.trdz.astro_days.databinding.FragmentWindowStartBinding
import com.trdz.astro_days.view.segment_picture.FragmentNavigation

class WindowStart: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!
	//endregion

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowStartBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.firstView.animate().apply {
			alpha(0.0f)
			duration = 3900L
			withEndAction { requireActivity().supportFragmentManager.beginTransaction().detach(this@WindowStart).commit()  }
			start()
		}
		Handler(Looper.getMainLooper()).postDelayed({
			executors.getNavigation().add(requireActivity().supportFragmentManager, FragmentNavigation.newInstance(true), false, R.id.container_fragment_navigation)
		}, 100L)
	}

	companion object {
		fun newInstance() = WindowStart()
	}
}

