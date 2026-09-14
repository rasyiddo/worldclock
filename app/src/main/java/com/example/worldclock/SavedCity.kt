package com.example.worldclock

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_cities")
data class SavedCity(

    @PrimaryKey
    val timezone: String,

    val city: String,

    val country: String,

    val flag: String
)