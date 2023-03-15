package com.trdz.astro_days.model

/** Ответ от Запроса в NASA EpicPicture,MarsRoverPicture,PictureOfTheDay */
data class RequestResult(
	val code: Int, 						//Код ответа на запрос
	val name: String = "", 				//Содержимое ответа: Заголовок о картинке
	val description: String = "", 		//Содержимое ответа: Описание картинки
	val url: String? = null, 			//Содержимое ответа: Сама картинке
	val type: String? = null,			//Проверка на пришедший формат
	val prefix: String = "NULL",		//В ответе указывается префикс запроса для уточнения ошибок
)
