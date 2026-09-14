package com.example.worldclock

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        FavoriteCity::class,
        SavedCity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteCityDao(): FavoriteCityDao

    abstract fun savedCityDao(): SavedCityDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 =
            object : Migration(1, 2) {

                override fun migrate(
                    database: SupportSQLiteDatabase
                ) {

                    database.execSQL(
                        """
                        CREATE TABLE IF NOT EXISTS saved_cities (
                            timezone TEXT NOT NULL,
                            city TEXT NOT NULL,
                            country TEXT NOT NULL,
                            flag TEXT NOT NULL,
                            PRIMARY KEY(timezone)
                        )
                        """.trimIndent()
                    )
                }
            }

        fun getInstance(
            context: Context
        ): AppDatabase {

            return INSTANCE
                ?: synchronized(this) {

                    val instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "worldclock_database"
                        )
                            .addMigrations(
                                MIGRATION_1_2
                            )
                            .build()

                    INSTANCE = instance

                    instance
                }
        }
    }
}