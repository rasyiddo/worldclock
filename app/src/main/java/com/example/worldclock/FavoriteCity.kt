package com.example.worldclock

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cities")
data class FavoriteCity(

    val timezone: String,

    val city: String,

    val country: String,

    val flag: String,

    @PrimaryKey
    val id: String = "$country|$city|$timezone"
)