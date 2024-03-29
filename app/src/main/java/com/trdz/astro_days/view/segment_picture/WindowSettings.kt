package com.trdz.astro_days.view.segment_picture

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.trdz.astro_days.R
import com.trdz.astro_days.databinding.FragmentWindowSettingsBinding
import com.trdz.astro_days.view.CustomOnBackPressed
import com.trdz.astro_days.view.MainActivity
import com.trdz.astro_days.view_model.MainViewModel
import com.trdz.astro_days.utility.KEY_OPTIONS
import com.trdz.astro_days.utility.KEY_THEME
import com.trdz.astro_days.utility.KEY_TSET
import com.trdz.astro_days.view_model.ViewModelFactories
import org.koin.android.ext.android.inject

class WindowSettings : Fragment(), CustomOnBackPressed {

    //region Injected
    private val factory: ViewModelFactories by inject()

    private val viewModel: MainViewModel by viewModels {
        factory
    }

    //endregion

    //region Elements
    private var _binding: FragmentWindowSettingsBinding? = null
    private val binding get() = _binding!!
    var themeID = 0

    //endregion

    //region Base realization
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onBackPressed(): Boolean {
        viewModel.changePage(1)
        return false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        getCurrentTheme()
        val localInflater = inflater.cloneInContext(ContextThemeWrapper(activity, getRealStylesID(themeID)))
        _binding = FragmentWindowSettingsBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun getCurrentTheme() {
        themeID = requireActivity().getSharedPreferences(KEY_OPTIONS,Context.MODE_PRIVATE).getInt(KEY_TSET,0)
    }

    private fun getRealStylesID(currentTheme: Int): Int {
        return when (currentTheme) {
            0 -> R.style.MyBaseTheme
            1 -> R.style.MyGoldTheme
            3 -> R.style.MyFiolTheme
            else -> 0
        }
    }

    //endregion

    //region Main functional
    /** Задание начального исполнения основного функционала*/
    private fun initialize() {
        preSelect()
        buttonBinds()
    }

    private fun preSelect() {
        val tab = binding.tabLayout.getTabAt(themeID)
        tab?.select()
    }

    private fun buttonBinds() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.changePage(-1)
                if (tab==null) return
                themeID = tab.position
                requireActivity().getSharedPreferences(KEY_OPTIONS, Context.MODE_PRIVATE).edit().putInt(KEY_TSET, themeID).apply()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment_base, WindowSettings())
                    .addToBackStack("")
                    .commit()
                requireActivity().onBackPressed()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.confirm.setOnClickListener {
            val parentActivity = (requireActivity() as MainActivity)
            parentActivity.getSharedPreferences(KEY_OPTIONS, Context.MODE_PRIVATE).edit().putInt(KEY_THEME, themeID).apply()
            parentActivity.recreate()
            requireActivity().onBackPressed()
        }
        binding.cancel.setOnClickListener {
            requireActivity().onBackPressed()}
    }

    //endregion

}