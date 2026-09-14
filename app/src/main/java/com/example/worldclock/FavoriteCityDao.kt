package com.example.worldclock

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {

    @Query("SELECT * FROM favorite_cities")
    fun getAllFavorites(): Flow<List<FavoriteCity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(
        city: FavoriteCity
    )

    @Query(
        "DELETE FROM favorite_cities WHERE timezone = :timezone"
    )
    suspend fun deleteFavorite(
        timezone: String
    )
}