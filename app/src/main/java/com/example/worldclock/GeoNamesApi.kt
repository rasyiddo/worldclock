package com.example.worldclock

import retrofit2.http.GET
import retrofit2.http.Query


/*
 * =========================================================
 * GEONAMES API
 * =========================================================
 */

interface GeoNamesApi {

    @GET("searchJSON")
    suspend fun searchCities(

        @Query("q")
        query: String,

        @Query("maxRows")
        maxRows: Int = 20,

        @Query("featureClass")
        featureClass: String = "P",

        @Query("orderby")
        orderBy: String = "population",

        @Query("style")
        style: String = "FULL",

        @Query("username")
        username: String
    ): GeoNamesSearchResponse


    @GET("timezoneJSON")
    suspend fun getTimezone(

        @Query("lat")
        latitude: Double,

        @Query("lng")
        longitude: Double,

        @Query("username")
        username: String
    ): GeoNamesTimezoneResponse
}