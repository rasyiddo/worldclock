package com.example.worldclock

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object GeoNamesService {

    val api: GeoNamesApi by lazy {

        Retrofit.Builder()

            .baseUrl(
                GeoNamesConfig.BASE_URL
            )

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()

            .create(
                GeoNamesApi::class.java
            )
    }
}