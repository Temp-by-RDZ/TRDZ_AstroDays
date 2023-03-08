package com.trdz.astro_days.model.server_mrp.dto

data class Photo(
	val camera: Camera,
	val earth_date: String,
	val id: Int,
	val img_src: String,
	val rover: Rover,
	val sol: Int
)