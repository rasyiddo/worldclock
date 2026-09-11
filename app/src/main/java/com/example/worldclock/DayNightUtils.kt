package com.example.worldclock

import java.time.Instant
import java.time.ZoneId

fun isDayTime(
    currentTimeMillis: Long,
    timezone: String
): Boolean {

    val instant =
        Instant.ofEpochMilli(
            currentTimeMillis
        )

    val time =
        instant.atZone(
            ZoneId.of(timezone)
        )

    return time.hour in 6..17
}