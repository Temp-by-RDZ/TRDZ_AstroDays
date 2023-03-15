package com.trdz.astro_days.view.segment_picture

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.trdz.astro_days.utility.CONST_MIN_ALPHA
import com.trdz.astro_days.utility.CONST_MIN_SCALE

class FragmentNavigationTransformer: ViewPager.PageTransformer {

	/** Настройка реакции страниц на перемещение */
	override fun transformPage(view: View, position: Float) {
		view.apply {
			when {
				//Действие когда страница за экраном слева
				position < -1 -> alpha = 0f
				//Действие с видимыми страницами
				position <= 1 -> {
					// Modify the default slide transition to shrink the page as well
					val scaleFactor = Math.max(CONST_MIN_SCALE, 1 - Math.abs(position))
					val vertMargin = height * (1 - scaleFactor) / 2
					val horzMargin = width * (1 - scaleFactor) / 2
					translationX = if (position < 0) horzMargin - vertMargin / 2
					else horzMargin + vertMargin / 2

					// Scale the page down (between MIN_SCALE and 1)
					scaleX = scaleFactor
					scaleY = scaleFactor

					// Fade the page relative to its size.
					alpha = (CONST_MIN_ALPHA + (((scaleFactor - CONST_MIN_SCALE) / (1 - CONST_MIN_SCALE)) * (1 - CONST_MIN_ALPHA)))
				}
				//Действие когда страница за экраном справа
				else -> alpha = 0f
			}
		}
	}
}
