package com.trdz.astro_days.view.segment_book

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trdz.astro_days.R
import com.trdz.astro_days.databinding.FragmentNavigationGlobalBinding
import com.trdz.astro_days.utility.EFFECT_MOVED
import com.trdz.astro_days.utility.EFFECT_MOVEL
import com.trdz.astro_days.utility.EFFECT_MOVER
import com.trdz.astro_days.utility.getSelectedItem
import com.trdz.astro_days.view.CustomOnBackPressed
import com.trdz.astro_days.view_model.MainViewModel
import com.trdz.astro_days.view.Navigation
import org.koin.android.ext.android.inject

class FragmentNavigation: Fragment(), CustomOnBackPressed {

	//region Injected
	private val navigation: Navigation by inject()

	//endregion

	//region Elements
	private var _binding: FragmentNavigationGlobalBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!

	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_viewModel = null
	}

	override fun onBackPressed(): Boolean {
		binding.fadeLayout.animate().alpha(1f).setDuration(1000L).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				for (fragment in requireActivity().supportFragmentManager.fragments) requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
				navigation.replace(requireActivity().supportFragmentManager, com.trdz.astro_days.view.segment_picture.FragmentNavigation(), false, R.id.container_fragment_navigation)
			}
		})
		return true
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentNavigationGlobalBinding.inflate(inflater, container, false)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
		initialization()
	}

	//endregion

	//region Main functional
	private fun initialization() {
		binding.bottomNavigation.setOnItemSelectedListener { item ->
			if (!item.isChecked) {
				when (item.itemId) {
					R.id.action_bottom_navigation_picture -> {
						navigation.replace(requireActivity().supportFragmentManager, WindowPicture(), false, -1, EFFECT_MOVER)
					}
					R.id.action_bottom_navigation_note -> {
						when (binding.bottomNavigation.getSelectedItem()) {
							R.id.action_bottom_navigation_knowledge -> navigation.replace(requireActivity().supportFragmentManager, WindowSecond(), false, -1, EFFECT_MOVER)
							R.id.action_bottom_navigation_picture -> navigation.replace(requireActivity().supportFragmentManager, WindowSecond(), false, -1, EFFECT_MOVEL)
							else -> navigation.replace(requireActivity().supportFragmentManager, WindowSecond(), false, -1, EFFECT_MOVED)
						}
					}
					R.id.action_bottom_navigation_knowledge -> {
						navigation.replace(requireActivity().supportFragmentManager, WindowThird(), false, -1, EFFECT_MOVEL)
					}
				}
			}
			true
		}
	}

	private fun buttonBinds() {
	}

	//endregion

	companion object {
		fun newInstance() = FragmentNavigation()
	}


}