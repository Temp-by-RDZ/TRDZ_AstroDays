package com.trdz.astro_days.model

/** Ответ от Запроса в NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
data class ServersResult(
	val code: Int, 						//Код ответа на запрос
	val name: String? = null, 			//Содержимое ответа: Заголовок о картинке
	val description: String? = null, 	//Содержимое ответа: Описание картинки
	val url: String? = null, 			//Содержимое ответа: Сама картинке
	val type: String? = null			//Проверка на пришедший формат
)
