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
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteCityDao(): FavoriteCityDao

    abstract fun savedCityDao(): SavedCityDao


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null


        /*
         * =====================================================
         * MIGRATION 1 → 2
         * =====================================================
         *
         * Migration lama.
         *
         * Pada versi 2 kita menambahkan
         * tabel saved_cities.
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {

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


        /*
         * =====================================================
         * MIGRATION 2 → 3
         * =====================================================
         *
         * Sekarang Primary Key bukan lagi timezone.
         *
         * Primary Key baru:
         *
         * id
         *
         * Dengan begitu:
         *
         * Madrid      → id berbeda
         * Barcelona   → id berbeda
         *
         * walaupun timezone mereka sama.
         */
        private val MIGRATION_2_3 = object : Migration(2, 3) {

            override fun migrate(
                database: SupportSQLiteDatabase
            ) {


                /*
                 * =================================================
                 * SAVED CITIES
                 * =================================================
                 */


                /*
                 * Buat tabel baru.
                 */
                database.execSQL(
                    """
                    CREATE TABLE saved_cities_new (
                        id TEXT NOT NULL,
                        timezone TEXT NOT NULL,
                        city TEXT NOT NULL,
                        country TEXT NOT NULL,
                        flag TEXT NOT NULL,
                        PRIMARY KEY(id)
                    )
                    """.trimIndent()
                )


                /*
                 * Copy data lama
                 * ke tabel baru.
                 *
                 * Data lama belum punya id.
                 *
                 * Karena itu kita buat
                 * id sementara:
                 *
                 * legacy_<timezone>
                 */
                database.execSQL(
                    """
                    INSERT INTO saved_cities_new (
                        id,
                        timezone,
                        city,
                        country,
                        flag
                    )
                    SELECT
                        'legacy_' || timezone,
                        timezone,
                        city,
                        country,
                        flag
                    FROM saved_cities
                    """.trimIndent()
                )


                /*
                 * Hapus tabel lama.
                 */
                database.execSQL(
                    "DROP TABLE saved_cities"
                )


                /*
                 * Rename tabel baru
                 * menjadi saved_cities.
                 */
                database.execSQL(
                    """
                    ALTER TABLE saved_cities_new
                    RENAME TO saved_cities
                    """.trimIndent()
                )


                /*
                 * =================================================
                 * FAVORITE CITIES
                 * =================================================
                 */


                /*
                 * Buat tabel favorite baru.
                 */
                database.execSQL(
                    """
                    CREATE TABLE favorite_cities_new (
                        id TEXT NOT NULL,
                        timezone TEXT NOT NULL,
                        city TEXT NOT NULL,
                        country TEXT NOT NULL,
                        flag TEXT NOT NULL,
                        PRIMARY KEY(id)
                    )
                    """.trimIndent()
                )


                /*
                 * Copy favorite lama
                 * ke tabel baru.
                 */
                database.execSQL(
                    """
                    INSERT INTO favorite_cities_new (
                        id,
                        timezone,
                        city,
                        country,
                        flag
                    )
                    SELECT
                        'legacy_' || timezone,
                        timezone,
                        city,
                        country,
                        flag
                    FROM favorite_cities
                    """.trimIndent()
                )


                /*
                 * Hapus tabel favorite lama.
                 */
                database.execSQL(
                    "DROP TABLE favorite_cities"
                )


                /*
                 * Rename tabel baru
                 * menjadi favorite_cities.
                 */
                database.execSQL(
                    """
                    ALTER TABLE favorite_cities_new
                    RENAME TO favorite_cities
                    """.trimIndent()
                )
            }
        }


        /*
         * =====================================================
         * GET DATABASE INSTANCE
         * =====================================================
         */
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

                            /*
                             * Daftarkan semua migration.
                             */
                            .addMigrations(
                                MIGRATION_1_2,
                                MIGRATION_2_3
                            )

                            .build()


                    INSTANCE = instance

                    instance
                }
        }
    }
}