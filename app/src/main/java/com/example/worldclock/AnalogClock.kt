package com.example.worldclock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalogClock(
    currentTimeMillis: Long,
    timezone: String,
    modifier: Modifier = Modifier
) {

    // Ambil warna dari Theme
    // SEBELUM masuk ke Canvas
    val outlineColor =
        MaterialTheme
            .colorScheme
            .outline

    val surfaceColor =
        MaterialTheme
            .colorScheme
            .onSurface

    val primaryColor =
        MaterialTheme
            .colorScheme
            .primary

    val instant =
        Instant.ofEpochMilli(
            currentTimeMillis
        )

    val time =
        ZonedDateTime.ofInstant(
            instant,
            ZoneId.of(timezone)
        )

    val hour =
        time.hour

    val minute =
        time.minute

    val second =
        time.second

    Canvas(
        modifier =
            modifier.size(150.dp)
    ) {

        val center =
            Offset(
                size.width / 2f,
                size.height / 2f
            )

        val radius =
            size.minDimension / 2f - 8f

        // ========================================
        // OUTER CLOCK CIRCLE
        // ========================================

        drawCircle(

            color =
                outlineColor,

            radius =
                radius,

            center =
                center,

            style =
                Stroke(
                    width = 3f
                )
        )

        // ========================================
        // HOUR MARKS
        // ========================================

        for (i in 0 until 12) {

            val angle =
                Math.toRadians(
                    (i * 30 - 90).toDouble()
                )

            val startRadius =
                radius - 10f

            val endRadius =
                radius - 3f

            val start =
                Offset(

                    x =
                        center.x +
                                cos(angle)
                                    .toFloat() *
                                startRadius,

                    y =
                        center.y +
                                sin(angle)
                                    .toFloat() *
                                startRadius
                )

            val end =
                Offset(

                    x =
                        center.x +
                                cos(angle)
                                    .toFloat() *
                                endRadius,

                    y =
                        center.y +
                                sin(angle)
                                    .toFloat() *
                                endRadius
                )

            drawLine(

                color =
                    outlineColor,

                start =
                    start,

                end =
                    end,

                strokeWidth =
                    4f,

                cap =
                    StrokeCap.Round
            )
        }

        // ========================================
        // HOUR HAND
        // ========================================

        val hourAngle =
            Math.toRadians(

                (
                        (hour % 12) * 30 +
                                minute * 0.5 -
                                90
                        ).toDouble()
            )

        val hourLength =
            radius * 0.5f

        val hourEnd =
            Offset(

                x =
                    center.x +
                            cos(hourAngle)
                                .toFloat() *
                            hourLength,

                y =
                    center.y +
                            sin(hourAngle)
                                .toFloat() *
                            hourLength
            )

        drawLine(

            color =
                surfaceColor,

            start =
                center,

            end =
                hourEnd,

            strokeWidth =
                7f,

            cap =
                StrokeCap.Round
        )

        // ========================================
        // MINUTE HAND
        // ========================================

        val minuteAngle =
            Math.toRadians(

                (
                        minute * 6 +
                                second * 0.1 -
                                90
                        ).toDouble()
            )

        val minuteLength =
            radius * 0.7f

        val minuteEnd =
            Offset(

                x =
                    center.x +
                            cos(minuteAngle)
                                .toFloat() *
                            minuteLength,

                y =
                    center.y +
                            sin(minuteAngle)
                                .toFloat() *
                            minuteLength
            )

        drawLine(

            color =
                surfaceColor,

            start =
                center,

            end =
                minuteEnd,

            strokeWidth =
                5f,

            cap =
                StrokeCap.Round
        )

        // ========================================
        // SECOND HAND
        // ========================================

        val secondAngle =
            Math.toRadians(

                (
                        second * 6 -
                                90
                        ).toDouble()
            )

        val secondLength =
            radius * 0.8f

        val secondEnd =
            Offset(

                x =
                    center.x +
                            cos(secondAngle)
                                .toFloat() *
                            secondLength,

                y =
                    center.y +
                            sin(secondAngle)
                                .toFloat() *
                            secondLength
            )

        drawLine(

            color =
                primaryColor,

            start =
                center,

            end =
                secondEnd,

            strokeWidth =
                3f,

            cap =
                StrokeCap.Round
        )

        // ========================================
        // CENTER DOT
        // ========================================

        drawCircle(

            color =
                primaryColor,

            radius =
                6f,

            center =
                center
        )
    }
}