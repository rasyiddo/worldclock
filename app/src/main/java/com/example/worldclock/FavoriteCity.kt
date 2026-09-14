package com.example.worldclock

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cities")
data class FavoriteCity(

    @PrimaryKey
    val timezone: String,

    val city: String,

    val country: String,

    val flag: String
)