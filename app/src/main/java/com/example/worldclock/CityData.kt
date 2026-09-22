package com.example.worldclock

data class ClockCity(
    val city: String,
    val country: String,
    val flag: String,
    val timezone: String,
    val id: String = "$country|$city|$timezone"
)