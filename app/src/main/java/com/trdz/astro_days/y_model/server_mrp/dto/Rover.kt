package com.trdz.astro_days.y_model.server_mrp.dto

data class Rover(
    val id: Int,
    val landing_date: String,
    val launch_date: String,
    val name: String,
    val status: String
)