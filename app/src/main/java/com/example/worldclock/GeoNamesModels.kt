package com.example.worldclock


/*
 * =========================================================
 * SEARCH RESPONSE
 * =========================================================
 */

data class GeoNamesSearchResponse(

    val totalResultsCount: Int = 0,

    val geonames: List<GeoNameResult> = emptyList()
)


/*
 * =========================================================
 * CITY RESULT
 * =========================================================
 */

data class GeoNameResult(

    val geonameId: Int = 0,

    val name: String = "",

    val toponymName: String = "",

    val countryCode: String = "",

    val countryName: String = "",

    val lat: String = "",

    val lng: String = "",

    val population: Long = 0,

    val fcl: String = "",

    val fcode: String = ""
)


/*
 * =========================================================
 * TIMEZONE RESPONSE
 * =========================================================
 */

data class GeoNamesTimezoneResponse(

    val countryCode: String = "",

    val countryName: String = "",

    val timezoneId: String = "",

    val time: String = "",

    val sunrise: String = "",

    val sunset: String = "",

    val rawOffset: Double = 0.0,

    val gmtOffset: Double = 0.0,

    val dstOffset: Double = 0.0
)