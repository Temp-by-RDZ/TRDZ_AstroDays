package com.trdz.astro_days.model.server_epc.dto

data class ResponseDataEPCItem(
	val attitude_quaternions: AttitudeQuaternions,
	val caption: String,
	val centroid_coordinates: CentroidCoordinates,
	val coords: Coords,
	val date: String,
	val dscovr_j2000_position: DscovrJ2000Position,
	val identifier: String,
	val image: String,
	val lunar_j2000_position: LunarJ2000Position,
	val sun_j2000_position: SunJ2000Position,
	val version: String
)