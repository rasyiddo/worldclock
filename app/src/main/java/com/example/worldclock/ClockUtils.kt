package com.example.worldclock

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun getCurrentTime(
    currentTimeMillis: Long,
    zoneId: String
): String {

    val instant = Instant.ofEpochMilli(currentTimeMillis)

    val time = ZonedDateTime.ofInstant(
        instant,
        ZoneId.of(zoneId)
    )

    val formatter = DateTimeFormatter.ofPattern(
        "HH:mm:ss"
    )

    return time.format(formatter)
}


fun getGmtOffset(
    currentTimeMillis: Long,
    zoneId: String
): String {

    val instant = Instant.ofEpochMilli(currentTimeMillis)

    val time = ZonedDateTime.ofInstant(
        instant,
        ZoneId.of(zoneId)
    )

    val totalSeconds = time.offset.totalSeconds

    val totalMinutes = totalSeconds / 60

    val hours = totalMinutes / 60
    val minutes = kotlin.math.abs(totalMinutes % 60)

    return if (minutes == 0) {
        "GMT ${if (hours >= 0) "+" else ""}$hours"
    } else {
        "GMT ${if (hours >= 0) "+" else ""}$hours:${minutes.toString().padStart(2, '0')}"
    }
}