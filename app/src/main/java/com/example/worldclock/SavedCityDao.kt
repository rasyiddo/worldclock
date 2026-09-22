package com.example.worldclock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedCityDao {

    @Query("SELECT * FROM saved_cities")
    fun getAllSavedCities(): Flow<List<SavedCity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedCity(city: SavedCity)

    @Query("DELETE FROM saved_cities WHERE id = :id")
    suspend fun deleteSavedCity(id: String)
}