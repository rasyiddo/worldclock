package com.example.worldclock

data class ClockCity(
    val city: String,
    val country: String,
    val flag: String,
    val timezone: String
)

val cityCatalog = listOf(

    // =====================================================
    // INDONESIA 🇮🇩
    // =====================================================

    ClockCity("Jakarta", "Indonesia", "🇮🇩", "Asia/Jakarta"),
    ClockCity("Surabaya", "Indonesia", "🇮🇩", "Asia/Jakarta"),
    ClockCity("Bandung", "Indonesia", "🇮🇩", "Asia/Jakarta"),
    ClockCity("Medan", "Indonesia", "🇮🇩", "Asia/Jakarta"),
    ClockCity("Semarang", "Indonesia", "🇮🇩", "Asia/Jakarta"),
    ClockCity("Makassar", "Indonesia", "🇮🇩", "Asia/Makassar"),
    ClockCity("Denpasar", "Indonesia", "🇮🇩", "Asia/Makassar"),
    ClockCity("Jayapura", "Indonesia", "🇮🇩", "Asia/Jayapura"),

    // =====================================================
    // SOUTHEAST ASIA
    // =====================================================

    ClockCity("Singapore", "Singapore", "🇸🇬", "Asia/Singapore"),
    ClockCity("Kuala Lumpur", "Malaysia", "🇲🇾", "Asia/Kuala_Lumpur"),
    ClockCity("Penang", "Malaysia", "🇲🇾", "Asia/Kuala_Lumpur"),
    ClockCity("Bangkok", "Thailand", "🇹🇭", "Asia/Bangkok"),
    ClockCity("Phuket", "Thailand", "🇹🇭", "Asia/Bangkok"),
    ClockCity("Hanoi", "Vietnam", "🇻🇳", "Asia/Bangkok"),
    ClockCity("Ho Chi Minh City", "Vietnam", "🇻🇳", "Asia/Ho_Chi_Minh"),
    ClockCity("Manila", "Philippines", "🇵🇭", "Asia/Manila"),
    ClockCity("Cebu City", "Philippines", "🇵🇭", "Asia/Manila"),
    ClockCity("Yangon", "Myanmar", "🇲🇲", "Asia/Yangon"),
    ClockCity("Phnom Penh", "Cambodia", "🇰🇭", "Asia/Phnom_Penh"),
    ClockCity("Vientiane", "Laos", "🇱🇦", "Asia/Vientiane"),
    ClockCity("Bandar Seri Begawan", "Brunei", "🇧🇳", "Asia/Brunei"),

    // =====================================================
    // EAST ASIA
    // =====================================================

    ClockCity("Tokyo", "Japan", "🇯🇵", "Asia/Tokyo"),
    ClockCity("Osaka", "Japan", "🇯🇵", "Asia/Tokyo"),
    ClockCity("Nagoya", "Japan", "🇯🇵", "Asia/Tokyo"),
    ClockCity("Seoul", "South Korea", "🇰🇷", "Asia/Seoul"),
    ClockCity("Busan", "South Korea", "🇰🇷", "Asia/Seoul"),
    ClockCity("Beijing", "China", "🇨🇳", "Asia/Shanghai"),
    ClockCity("Shanghai", "China", "🇨🇳", "Asia/Shanghai"),
    ClockCity("Shenzhen", "China", "🇨🇳", "Asia/Shanghai"),
    ClockCity("Hong Kong", "Hong Kong", "🇭🇰", "Asia/Hong_Kong"),
    ClockCity("Taipei", "Taiwan", "🇹🇼", "Asia/Taipei"),
    ClockCity("Ulaanbaatar", "Mongolia", "🇲🇳", "Asia/Ulaanbaatar"),

    // =====================================================
    // SOUTH ASIA
    // =====================================================

    ClockCity("New Delhi", "India", "🇮🇳", "Asia/Kolkata"),
    ClockCity("Mumbai", "India", "🇮🇳", "Asia/Kolkata"),
    ClockCity("Bengaluru", "India", "🇮🇳", "Asia/Kolkata"),
    ClockCity("Kolkata", "India", "🇮🇳", "Asia/Kolkata"),
    ClockCity("Dhaka", "Bangladesh", "🇧🇩", "Asia/Dhaka"),
    ClockCity("Kathmandu", "Nepal", "🇳🇵", "Asia/Kathmandu"),
    ClockCity("Colombo", "Sri Lanka", "🇱🇰", "Asia/Colombo"),
    ClockCity("Islamabad", "Pakistan", "🇵🇰", "Asia/Karachi"),
    ClockCity("Karachi", "Pakistan", "🇵🇰", "Asia/Karachi"),

    // =====================================================
    // MIDDLE EAST
    // =====================================================

    ClockCity("Dubai", "United Arab Emirates", "🇦🇪", "Asia/Dubai"),
    ClockCity("Abu Dhabi", "United Arab Emirates", "🇦🇪", "Asia/Dubai"),
    ClockCity("Doha", "Qatar", "🇶🇦", "Asia/Qatar"),
    ClockCity("Riyadh", "Saudi Arabia", "🇸🇦", "Asia/Riyadh"),
    ClockCity("Jeddah", "Saudi Arabia", "🇸🇦", "Asia/Riyadh"),
    ClockCity("Kuwait City", "Kuwait", "🇰🇼", "Asia/Kuwait"),
    ClockCity("Muscat", "Oman", "🇴🇲", "Asia/Muscat"),
    ClockCity("Manama", "Bahrain", "🇧🇭", "Asia/Bahrain"),
    ClockCity("Amman", "Jordan", "🇯🇴", "Asia/Amman"),
    ClockCity("Jerusalem", "Israel", "🇮🇱", "Asia/Jerusalem"),
    ClockCity("Beirut", "Lebanon", "🇱🇧", "Asia/Beirut"),
    ClockCity("Istanbul", "Turkey", "🇹🇷", "Europe/Istanbul"),

    // =====================================================
    // CENTRAL ASIA
    // =====================================================

    ClockCity("Almaty", "Kazakhstan", "🇰🇿", "Asia/Almaty"),
    ClockCity("Astana", "Kazakhstan", "🇰🇿", "Asia/Almaty"),
    ClockCity("Tashkent", "Uzbekistan", "🇺🇿", "Asia/Tashkent"),
    ClockCity("Bishkek", "Kyrgyzstan", "🇰🇬", "Asia/Bishkek"),

    // =====================================================
    // EUROPE - WEST
    // =====================================================

    ClockCity("London", "United Kingdom", "🇬🇧", "Europe/London"),
    ClockCity("Manchester", "United Kingdom", "🇬🇧", "Europe/London"),
    ClockCity("Paris", "France", "🇫🇷", "Europe/Paris"),
    ClockCity("Lyon", "France", "🇫🇷", "Europe/Paris"),
    ClockCity("Madrid", "Spain", "🇪🇸", "Europe/Madrid"),
    ClockCity("Barcelona", "Spain", "🇪🇸", "Europe/Madrid"),
    ClockCity("Lisbon", "Portugal", "🇵🇹", "Europe/Lisbon"),
    ClockCity("Amsterdam", "Netherlands", "🇳🇱", "Europe/Amsterdam"),
    ClockCity("Brussels", "Belgium", "🇧🇪", "Europe/Brussels"),
    ClockCity("Dublin", "Ireland", "🇮🇪", "Europe/Dublin"),

    // =====================================================
    // EUROPE - CENTRAL
    // =====================================================

    ClockCity("Berlin", "Germany", "🇩🇪", "Europe/Berlin"),
    ClockCity("Munich", "Germany", "🇩🇪", "Europe/Berlin"),
    ClockCity("Frankfurt", "Germany", "🇩🇪", "Europe/Berlin"),
    ClockCity("Vienna", "Austria", "🇦🇹", "Europe/Vienna"),
    ClockCity("Zurich", "Switzerland", "🇨🇭", "Europe/Zurich"),
    ClockCity("Geneva", "Switzerland", "🇨🇭", "Europe/Zurich"),
    ClockCity("Rome", "Italy", "🇮🇹", "Europe/Rome"),
    ClockCity("Milan", "Italy", "🇮🇹", "Europe/Rome"),
    ClockCity("Copenhagen", "Denmark", "🇩🇰", "Europe/Copenhagen"),
    ClockCity("Stockholm", "Sweden", "🇸🇪", "Europe/Stockholm"),
    ClockCity("Oslo", "Norway", "🇳🇴", "Europe/Oslo"),
    ClockCity("Helsinki", "Finland", "🇫🇮", "Europe/Helsinki"),

    // =====================================================
    // EUROPE - EAST
    // =====================================================

    ClockCity("Moscow", "Russia", "🇷🇺", "Europe/Moscow"),
    ClockCity("Saint Petersburg", "Russia", "🇷🇺", "Europe/Moscow"),
    ClockCity("Kyiv", "Ukraine", "🇺🇦", "Europe/Kyiv"),
    ClockCity("Warsaw", "Poland", "🇵🇱", "Europe/Warsaw"),
    ClockCity("Prague", "Czech Republic", "🇨🇿", "Europe/Prague"),
    ClockCity("Budapest", "Hungary", "🇭🇺", "Europe/Budapest"),
    ClockCity("Bucharest", "Romania", "🇷🇴", "Europe/Bucharest"),
    ClockCity("Athens", "Greece", "🇬🇷", "Europe/Athens"),

    // =====================================================
    // NORTH AMERICA
    // =====================================================

    ClockCity("New York", "United States", "🇺🇸", "America/New_York"),
    ClockCity("Washington D.C.", "United States", "🇺🇸", "America/New_York"),
    ClockCity("Boston", "United States", "🇺🇸", "America/New_York"),
    ClockCity("Miami", "United States", "🇺🇸", "America/New_York"),
    ClockCity("Atlanta", "United States", "🇺🇸", "America/New_York"),
    ClockCity("Chicago", "United States", "🇺🇸", "America/Chicago"),
    ClockCity("Houston", "United States", "🇺🇸", "America/Chicago"),
    ClockCity("Dallas", "United States", "🇺🇸", "America/Chicago"),
    ClockCity("Denver", "United States", "🇺🇸", "America/Denver"),
    ClockCity("Phoenix", "United States", "🇺🇸", "America/Phoenix"),
    ClockCity("Los Angeles", "United States", "🇺🇸", "America/Los_Angeles"),
    ClockCity("San Francisco", "United States", "🇺🇸", "America/Los_Angeles"),
    ClockCity("Seattle", "United States", "🇺🇸", "America/Los_Angeles"),
    ClockCity("Las Vegas", "United States", "🇺🇸", "America/Los_Angeles"),
    ClockCity("Anchorage", "United States", "🇺🇸", "America/Anchorage"),
    ClockCity("Toronto", "Canada", "🇨🇦", "America/Toronto"),
    ClockCity("Montreal", "Canada", "🇨🇦", "America/Toronto"),
    ClockCity("Vancouver", "Canada", "🇨🇦", "America/Vancouver"),
    ClockCity("Calgary", "Canada", "🇨🇦", "America/Edmonton"),
    ClockCity("Mexico City", "Mexico", "🇲🇽", "America/Mexico_City"),
    ClockCity("Cancun", "Mexico", "🇲🇽", "America/Cancun"),

    // =====================================================
    // SOUTH AMERICA
    // =====================================================

    ClockCity("São Paulo", "Brazil", "🇧🇷", "America/Sao_Paulo"),
    ClockCity("Rio de Janeiro", "Brazil", "🇧🇷", "America/Sao_Paulo"),
    ClockCity("Brasília", "Brazil", "🇧🇷", "America/Sao_Paulo"),
    ClockCity("Buenos Aires", "Argentina", "🇦🇷", "America/Argentina/Buenos_Aires"),
    ClockCity("Santiago", "Chile", "🇨🇱", "America/Santiago"),
    ClockCity("Lima", "Peru", "🇵🇪", "America/Lima"),
    ClockCity("Bogotá", "Colombia", "🇨🇴", "America/Bogota"),
    ClockCity("Caracas", "Venezuela", "🇻🇪", "America/Caracas"),
    ClockCity("Montevideo", "Uruguay", "🇺🇾", "America/Montevideo"),

    // =====================================================
    // AFRICA
    // =====================================================

    ClockCity("Cairo", "Egypt", "🇪🇬", "Africa/Cairo"),
    ClockCity("Alexandria", "Egypt", "🇪🇬", "Africa/Cairo"),
    ClockCity("Johannesburg", "South Africa", "🇿🇦", "Africa/Johannesburg"),
    ClockCity("Cape Town", "South Africa", "🇿🇦", "Africa/Johannesburg"),
    ClockCity("Nairobi", "Kenya", "🇰🇪", "Africa/Nairobi"),
    ClockCity("Lagos", "Nigeria", "🇳🇬", "Africa/Lagos"),
    ClockCity("Accra", "Ghana", "🇬🇭", "Africa/Accra"),
    ClockCity("Casablanca", "Morocco", "🇲🇦", "Africa/Casablanca"),
    ClockCity("Algiers", "Algeria", "🇩🇿", "Africa/Algiers"),
    ClockCity("Addis Ababa", "Ethiopia", "🇪🇹", "Africa/Addis_Ababa"),

    // =====================================================
    // AUSTRALIA / OCEANIA
    // =====================================================

    ClockCity("Sydney", "Australia", "🇦🇺", "Australia/Sydney"),
    ClockCity("Melbourne", "Australia", "🇦🇺", "Australia/Melbourne"),
    ClockCity("Brisbane", "Australia", "🇦🇺", "Australia/Brisbane"),
    ClockCity("Perth", "Australia", "🇦🇺", "Australia/Perth"),
    ClockCity("Adelaide", "Australia", "🇦🇺", "Australia/Adelaide"),
    ClockCity("Darwin", "Australia", "🇦🇺", "Australia/Darwin"),
    ClockCity("Auckland", "New Zealand", "🇳🇿", "Pacific/Auckland"),
    ClockCity("Wellington", "New Zealand", "🇳🇿", "Pacific/Auckland"),
    ClockCity("Honolulu", "United States", "🇺🇸", "Pacific/Honolulu"),
    ClockCity("Suva", "Fiji", "🇫🇯", "Pacific/Fiji")
)