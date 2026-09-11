package com.example.worldclock

data class ClockCity(
    val city: String,
    val country: String,
    val flag: String,
    val timezone: String
)

val cityCatalog = listOf(
    ClockCity("Jakarta", "Indonesia", "🇮🇩", "Asia/Jakarta"),
    ClockCity("Tokyo", "Japan", "🇯🇵", "Asia/Tokyo"),
    ClockCity("Singapore", "Singapore", "🇸🇬", "Asia/Singapore"),
    ClockCity("Seoul", "South Korea", "🇰🇷", "Asia/Seoul"),
    ClockCity("Bangkok", "Thailand", "🇹🇭", "Asia/Bangkok"),
    ClockCity("Dubai", "United Arab Emirates", "🇦🇪", "Asia/Dubai"),
    ClockCity("London", "United Kingdom", "🇬🇧", "Europe/London"),
    ClockCity("Paris", "France", "🇫🇷", "Europe/Paris"),
    ClockCity("Berlin", "Germany", "🇩🇪", "Europe/Berlin"),
    ClockCity("Amsterdam", "Netherlands", "🇳🇱", "Europe/Amsterdam"),
    ClockCity("Moscow", "Russia", "🇷🇺", "Europe/Moscow"),
    ClockCity("New York", "United States", "🇺🇸", "America/New_York"),
    ClockCity("Los Angeles", "United States", "🇺🇸", "America/Los_Angeles"),
    ClockCity("Chicago", "United States", "🇺🇸", "America/Chicago"),
    ClockCity("Toronto", "Canada", "🇨🇦", "America/Toronto"),
    ClockCity("Vancouver", "Canada", "🇨🇦", "America/Vancouver"),
    ClockCity("Mexico City", "Mexico", "🇲🇽", "America/Mexico_City"),
    ClockCity("São Paulo", "Brazil", "🇧🇷", "America/Sao_Paulo"),
    ClockCity("Buenos Aires", "Argentina", "🇦🇷", "America/Argentina/Buenos_Aires"),
    ClockCity("Sydney", "Australia", "🇦🇺", "Australia/Sydney"),
    ClockCity("Melbourne", "Australia", "🇦🇺", "Australia/Melbourne"),
    ClockCity("Perth", "Australia", "🇦🇺", "Australia/Perth"),
    ClockCity("Auckland", "New Zealand", "🇳🇿", "Pacific/Auckland")
)