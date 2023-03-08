package com.trdz.astro_days.view

/** Интерфейс для  фрагментов которым требуется нестандартная рреакция на кнопку "назад" */
interface CustomOnBackPressed {
	fun onBackPressed(): Boolean
}