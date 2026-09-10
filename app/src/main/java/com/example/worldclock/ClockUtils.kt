package com.example.worldclock

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getCurrentTime(zoneId: String): String {

    val time = ZonedDateTime.now(
        ZoneId.of(zoneId)
    )

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    return time.format(formatter)
}