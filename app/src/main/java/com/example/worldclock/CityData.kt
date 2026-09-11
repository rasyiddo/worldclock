package com.example.worldclock

data class ClockCity(
    val city: String,
    val country: String,
    val flag: String,
    val timezone: String
)

val cityCatalog = listOf(

    ClockCity(
        city = "Jakarta",
        country = "Indonesia",
        flag = "🇮🇩",
        timezone = "Asia/Jakarta"
    ),

    ClockCity(
        city = "Tokyo",
        country = "Japan",
        flag = "🇯🇵",
        timezone = "Asia/Tokyo"
    ),

    ClockCity(
        city = "Singapore",
        country = "Singapore",
        flag = "🇸🇬",
        timezone = "Asia/Singapore"
    ),

    ClockCity(
        city = "Seoul",
        country = "South Korea",
        flag = "🇰🇷",
        timezone = "Asia/Seoul"
    ),

    ClockCity(
        city = "Bangkok",
        country = "Thailand",
        flag = "🇹🇭",
        timezone = "Asia/Bangkok"
    ),

    ClockCity(
        city = "Dubai",
        country = "United Arab Emirates",
        flag = "🇦🇪",
        timezone = "Asia/Dubai"
    ),

    ClockCity(
        city = "London",
        country = "United Kingdom",
        flag = "🇬🇧",
        timezone = "Europe/London"
    ),

    ClockCity(
        city = "Paris",
        country = "France",
        flag = "🇫🇷",
        timezone = "Europe/Paris"
    ),

    ClockCity(
        city = "Berlin",
        country = "Germany",
        flag = "🇩🇪",
        timezone = "Europe/Berlin"
    ),

    ClockCity(
        city = "Amsterdam",
        country = "Netherlands",
        flag = "🇳🇱",
        timezone = "Europe/Amsterdam"
    ),

    ClockCity(
        city = "Moscow",
        country = "Russia",
        flag = "🇷🇺",
        timezone = "Europe/Moscow"
    ),

    ClockCity(
        city = "New York",
        country = "United States",
        flag = "🇺🇸",
        timezone = "America/New_York"
    ),

    ClockCity(
        city = "Los Angeles",
        country = "United States",
        flag = "🇺🇸",
        timezone = "America/Los_Angeles"
    ),

    ClockCity(
        city = "Chicago",
        country = "United States",
        flag = "🇺🇸",
        timezone = "America/Chicago"
    ),

    ClockCity(
        city = "Toronto",
        country = "Canada",
        flag = "🇨🇦",
        timezone = "America/Toronto"
    ),

    ClockCity(
        city = "Vancouver",
        country = "Canada",
        flag = "🇨🇦",
        timezone = "America/Vancouver"
    ),

    ClockCity(
        city = "Mexico City",
        country = "Mexico",
        flag = "🇲🇽",
        timezone = "America/Mexico_City"
    ),

    ClockCity(
        city = "São Paulo",
        country = "Brazil",
        flag = "🇧🇷",
        timezone = "America/Sao_Paulo"
    ),

    ClockCity(
        city = "Buenos Aires",
        country = "Argentina",
        flag = "🇦🇷",
        timezone = "America/Argentina/Buenos_Aires"
    ),

    ClockCity(
        city = "Sydney",
        country = "Australia",
        flag = "🇦🇺",
        timezone = "Australia/Sydney"
    ),

    ClockCity(
        city = "Melbourne",
        country = "Australia",
        flag = "🇦🇺",
        timezone = "Australia/Melbourne"
    ),

    ClockCity(
        city = "Perth",
        country = "Australia",
        flag = "🇦🇺",
        timezone = "Australia/Perth"
    ),

    ClockCity(
        city = "Auckland",
        country = "New Zealand",
        flag = "🇳🇿",
        timezone = "Pacific/Auckland"
    )

)