package com.trdz.astro_days.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.trdz.astro_days.R
import com.trdz.astro_days.utility.*

/** Кастомные комманды для навигаүии */
class Navigation(private var fastContainer: Int = 0) {

	/** Возврат к нужному окну в памяти */
	fun returnTo(manager: FragmentManager, toId: Int = 0) {
		if (manager.backStackEntryCount <= toId) return
		val entry: FragmentManager.BackStackEntry = manager.getBackStackEntryAt(toId)
		manager.popBackStackImmediate(entry.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
	}

	/** Добавление нового окна на экран */
	fun add(manager: FragmentManager, fragment: Fragment?, remember: Boolean = true, container: Int = fastContainer, effect: String = "NONE") {
		if (manager.isDestroyed) return
		manager.beginTransaction().apply {
			animate(effect)
			if (container == -1) add(fastContainer, fragment!!)
			else add(container, fragment!!)
			if (remember) addToBackStack("")
			commit()
		}
	}

	/** Замена текущего на другое окно */
	fun replace(manager: FragmentManager, fragment: Fragment?, remember: Boolean = true, container: Int = fastContainer, effect: String = "NONE") {
		if (manager.isDestroyed) return
		manager.beginTransaction().apply {
			animate(effect)
			if (container == -1) replace(fastContainer, fragment!!)
			else replace(container, fragment!!)
			setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
			if (remember) addToBackStack("")
			commit()
		}
	}

}