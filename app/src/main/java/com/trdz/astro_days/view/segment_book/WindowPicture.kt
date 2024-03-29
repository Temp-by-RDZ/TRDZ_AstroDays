package com.trdz.astro_days.view.segment_book

import android.graphics.BlurMaskFilter
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.*
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.astro_days.R
import com.trdz.astro_days.view.MainActivity
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.trdz.astro_days.databinding.FragmentWindowPictureBinding
import com.trdz.astro_days.view.Navigation
import com.trdz.astro_days.view.segment_picture.FragmentNavigation
import org.koin.android.ext.android.inject
import kotlin.concurrent.thread
import kotlin.math.ceil

class WindowPicture: Fragment() {

	//region Injected

	private val navigation: Navigation by inject()

	//endregion

	//region Elements
	private var _binding: FragmentWindowPictureBinding? = null
	private val binding get() = _binding!!
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPictureBinding.inflate(inflater, container, false)
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
		binding.goToPicture.setOnClickListener {
			goToPicture()
		}
	}

	private fun initialize() {

		val textSpannable = binding.placeholder.text

		val spannedString: SpannedString
		val spannableString: SpannableString = SpannableString(textSpannable)
		var spannableStringBuilder: SpannableStringBuilder = SpannableStringBuilder(textSpannable)

		binding.placeholder.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
		spannableStringBuilder = (binding.placeholder.text as SpannableStringBuilder).apply {
			setSpan(RelativeSizeSpan(1.3f), 0, spannableStringBuilder.length, SpannedString.SPAN_INCLUSIVE_INCLUSIVE)
			val color = resources.getIntArray(R.array.colors)
			var start = 0
			for (j in color.indices) {
				val end = kotlin.math.min((start + ceil(textSpannable.length.toDouble() / color.size)).toInt(), textSpannable.length)
				setSpan(ForegroundColorSpan(color[j]), start, end, SpannedString.SPAN_EXCLUSIVE_EXCLUSIVE)
				start = end
			}
			insert(0, "|")
			insert(textSpannable.length + 1, "|")
			setSpan(StyleSpan(Typeface.BOLD), 0, textSpannable.length + 1,0)
			setSpan(UnderlineSpan(), 0, textSpannable.length + 1, 0)
			val blurMaskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.SOLID)
			setSpan(MaskFilterSpan(blurMaskFilter), 0, textSpannable.length + 1, 0)

		}


	}

	private fun goToPicture() {
		navigation.replace(requireActivity().supportFragmentManager, FragmentNavigation(), false, R.id.container_fragment_navigation)

		val dispose = AlphaAnimation(1.0f, 0.0f).apply {
			duration = 2000
			fillAfter = true
		}
		binding.goToPicture.startAnimation(dispose)
		thread {
			while (!dispose.hasEnded()) Thread.sleep(50L)
			requireActivity().supportFragmentManager.beginTransaction().detach(this).commit()
		}

	}

	//endregion

	companion object {
		fun newInstance() = WindowPicture()
	}

}