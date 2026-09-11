package com.example.worldclock

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worldclock.ui.theme.WorldClockTheme
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.TimeZone


class MainActivity : ComponentActivity() {

    private var locationName by mutableStateOf(
        "Detecting location..."
    )

    private var countryName by mutableStateOf(
        "Please wait..."
    )

    private var detectedTimezone by mutableStateOf(
        TimeZone.getDefault().id
    )

    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineGranted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true

            val coarseGranted =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] == true

            if (fineGranted || coarseGranted) {

                detectLocation()

            } else {

                locationName =
                    "Location permission denied"

                countryName =
                    "Please enable location permission"
            }
        }


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        requestLocationPermission()

        setContent {

            WorldClockTheme {

                WorldClockApp(

                    locationName =
                        locationName,

                    countryName =
                        countryName,

                    timezone =
                        detectedTimezone,

                    onRefreshLocation = {
                        detectLocation()
                    }
                )
            }
        }
    }


    private fun requestLocationPermission() {

        val fineGranted =
            checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted =
            checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        if (fineGranted || coarseGranted) {

            detectLocation()

        } else {

            locationPermissionLauncher.launch(

                arrayOf(

                    Manifest.permission.ACCESS_FINE_LOCATION,

                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }


    private fun detectLocation() {

        locationName =
            "Detecting location..."

        countryName =
            "Please wait..."


        val locationManager =
            getSystemService(
                LOCATION_SERVICE
            ) as LocationManager


        val provider = when {

            locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            ) ->
                LocationManager.GPS_PROVIDER

            locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            ) ->
                LocationManager.NETWORK_PROVIDER

            else ->
                null
        }


        if (provider == null) {

            locationName =
                "Location unavailable"

            countryName =
                "Please enable GPS"

            return
        }


        try {

            val location =
                locationManager.getLastKnownLocation(
                    provider
                )


            if (location != null) {

                val latitude =
                    location.latitude

                val longitude =
                    location.longitude


                detectedTimezone =
                    TimeZone.getDefault().id


                getAddressFromLocation(
                    latitude,
                    longitude
                )

            } else {

                locationName =
                    "Location unavailable"

                countryName =
                    "Try again with GPS enabled"
            }

        } catch (e: SecurityException) {

            locationName =
                "Location permission error"

            countryName =
                "Please check permissions"
        }
    }


    private fun getAddressFromLocation(
        latitude: Double,
        longitude: Double
    ) {

        try {

            val geocoder =
                Geocoder(
                    this,
                    Locale.getDefault()
                )


            @Suppress("DEPRECATION")

            val addresses =
                geocoder.getFromLocation(
                    latitude,
                    longitude,
                    1
                )


            if (!addresses.isNullOrEmpty()) {

                val address =
                    addresses[0]


                locationName =
                    address.locality
                        ?: address.subAdminArea
                                ?: address.adminArea
                                ?: "Unknown location"


                countryName =
                    address.countryName
                        ?: "Unknown country"

            } else {

                locationName =
                    "Unknown location"

                countryName =
                    "Unknown country"
            }

        } catch (e: Exception) {

            locationName =
                "Unable to detect"

            countryName =
                "Please try again"
        }
    }
}


/*
 * ============================================================
 * MAIN APP
 * ============================================================
 */

@Composable
fun WorldClockApp(
    locationName: String,
    countryName: String,
    timezone: String,
    onRefreshLocation: () -> Unit
) {

    /*
     * false = Home
     * true  = Search City
     */

    var showSearchScreen by remember {
        mutableStateOf(false)
    }


    if (showSearchScreen) {

        SearchCityScreen(

            onBack = {

                showSearchScreen = false
            },

            onCitySelected = { city ->

                /*
                 * Step 9 nanti:
                 *
                 * city akan dimasukkan
                 * ke World Clock.
                 *
                 * Untuk sekarang kembali
                 * ke Home dulu.
                 */

                showSearchScreen = false
            }
        )

    } else {

        WorldClockHomeScreen(

            locationName =
                locationName,

            countryName =
                countryName,

            timezone =
                timezone,

            onRefreshLocation = {

                onRefreshLocation()
            },

            onAddCity = {

                showSearchScreen = true
            }
        )
    }
}


/*
 * ============================================================
 * HOME SCREEN
 * ============================================================
 */

@Composable
fun WorldClockHomeScreen(
    locationName: String,
    countryName: String,
    timezone: String,
    onRefreshLocation: () -> Unit,
    onAddCity: () -> Unit
) {

    var currentTimeMillis by remember {

        mutableLongStateOf(
            System.currentTimeMillis()
        )
    }


    var isAnalog by remember {

        mutableStateOf(false)
    }


    /*
     * REAL-TIME CLOCK
     */

    LaunchedEffect(Unit) {

        while (true) {

            currentTimeMillis =
                System.currentTimeMillis()

            delay(1000)
        }
    }


    /*
     * DEFAULT WORLD CLOCKS
     */

    val cities = listOf(

        ClockCity(
            city = "Tokyo",
            country = "Japan",
            flag = "🇯🇵",
            timezone = "Asia/Tokyo"
        ),

        ClockCity(
            city = "London",
            country = "United Kingdom",
            flag = "🇬🇧",
            timezone = "Europe/London"
        ),

        ClockCity(
            city = "New York",
            country = "United States",
            flag = "🇺🇸",
            timezone = "America/New_York"
        )
    )


    Scaffold(

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    onAddCity()
                }

            ) {

                Icon(

                    imageVector =
                        Icons.Default.Add,

                    contentDescription =
                        "Add city"
                )
            }
        }

    ) { innerPadding ->


        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {


            /*
             * HEADER
             */

            item {

                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                Text(

                    text =
                        "WorldClock",

                    fontSize =
                        32.sp,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                Text(

                    text =
                        "Track time around the world",

                    fontSize =
                        14.sp,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }


            /*
             * YOUR LOCATION
             */

            item {

                YourLocationCard(

                    locationName =
                        locationName,

                    countryName =
                        countryName,

                    timezone =
                        timezone,

                    onRefresh =
                        onRefreshLocation,

                    currentTimeMillis =
                        currentTimeMillis
                )
            }


            /*
             * CLOCK STYLE
             */

            item {

                ClockDisplaySelector(

                    isAnalog =
                        isAnalog,

                    onModeChange = {

                        isAnalog = it
                    }
                )
            }


            /*
             * WORLD CLOCK TITLE
             */

            item {

                Text(

                    text =
                        "World clocks",

                    fontSize =
                        20.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }


            /*
             * CITY CARDS
             */

            items(

                items =
                    cities

            ) { city ->

                CityClockCard(

                    city =
                        city,

                    currentTimeMillis =
                        currentTimeMillis,

                    isAnalog =
                        isAnalog
                )
            }


            item {

                Spacer(
                    modifier =
                        Modifier.height(80.dp)
                )
            }
        }
    }
}


/*
 * ============================================================
 * YOUR LOCATION CARD
 * ============================================================
 */

@Composable
fun YourLocationCard(
    locationName: String,
    countryName: String,
    timezone: String,
    onRefresh: () -> Unit,
    currentTimeMillis: Long
) {

    val currentTime =
        getCurrentTime(

            currentTimeMillis =
                currentTimeMillis,

            zoneId =
                timezone
        )


    val gmtOffset =
        getGmtOffset(

            currentTimeMillis =
                currentTimeMillis,

            zoneId =
                timezone
        )


    val isDay =
        isDayTime(

            currentTimeMillis =
                currentTimeMillis,

            timezone =
                timezone
        )


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            MaterialTheme.shapes.extraLarge,

        colors =
            CardDefaults.cardColors(

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .primaryContainer
            )
    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
        ) {


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {


                Icon(

                    imageVector =
                        Icons.Default.LocationOn,

                    contentDescription =
                        "Location",

                    tint =
                        MaterialTheme
                            .colorScheme
                            .primary
                )


                Spacer(
                    modifier =
                        Modifier.size(10.dp)
                )


                Column(

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            "Your Location",

                        fontSize =
                            13.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onPrimaryContainer
                    )


                    Text(

                        text =
                            locationName,

                        fontSize =
                            20.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Text(

                        text =
                            countryName,

                        fontSize =
                            13.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onPrimaryContainer
                    )
                }


                IconButton(

                    onClick = {

                        onRefresh()
                    }

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Refresh,

                        contentDescription =
                            "Refresh location"
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.Bottom
            ) {


                Column(

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            currentTime,

                        fontSize =
                            36.sp,

                        fontWeight =
                            FontWeight.Light
                    )


                    Text(

                        text =
                            timezone,

                        fontSize =
                            12.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onPrimaryContainer
                    )
                }


                Column(

                    horizontalAlignment =
                        Alignment.End
                ) {

                    Text(

                        text =
                            if (isDay)
                                "☀️ Day"
                            else
                                "🌙 Night",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium
                    )


                    Text(

                        text =
                            gmtOffset,

                        fontSize =
                            12.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }
            }
        }
    }
}


/*
 * ============================================================
 * DIGITAL / ANALOG SELECTOR
 * ============================================================
 */

@Composable
fun ClockDisplaySelector(
    isAnalog: Boolean,
    onModeChange: (Boolean) -> Unit
) {

    Column(

        modifier =
            Modifier.fillMaxWidth()
    ) {


        Text(

            text =
                "Clock style",

            fontSize =
                14.sp,

            fontWeight =
                FontWeight.SemiBold,

            color =
                MaterialTheme
                    .colorScheme
                    .onBackground
        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        SingleChoiceSegmentedButtonRow(

            modifier =
                Modifier.fillMaxWidth()
        ) {


            SegmentedButton(

                selected =
                    !isAnalog,

                onClick = {

                    onModeChange(false)
                },

                shape =
                    SegmentedButtonDefaults
                        .itemShape(

                            index = 0,

                            count = 2
                        )

            ) {

                Text(
                    text = "Digital"
                )
            }


            SegmentedButton(

                selected =
                    isAnalog,

                onClick = {

                    onModeChange(true)
                },

                shape =
                    SegmentedButtonDefaults
                        .itemShape(

                            index = 1,

                            count = 2
                        )

            ) {

                Text(
                    text = "Analog"
                )
            }
        }
    }
}


/*
 * ============================================================
 * CITY CLOCK CARD
 * ============================================================
 */

@Composable
fun CityClockCard(
    city: ClockCity,
    currentTimeMillis: Long,
    isAnalog: Boolean
) {

    val currentTime =
        getCurrentTime(

            currentTimeMillis =
                currentTimeMillis,

            zoneId =
                city.timezone
        )


    val gmtOffset =
        getGmtOffset(

            currentTimeMillis =
                currentTimeMillis,

            zoneId =
                city.timezone
        )


    val isDay =
        isDayTime(

            currentTimeMillis =
                currentTimeMillis,

            timezone =
                city.timezone
        )


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            MaterialTheme.shapes.extraLarge,

        colors =
            CardDefaults.cardColors(

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface
            ),

        elevation =
            CardDefaults.cardElevation(

                defaultElevation =
                    2.dp
            )
    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
        ) {


            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {


                Text(

                    text =
                        city.flag,

                    fontSize =
                        32.sp
                )


                Spacer(
                    modifier =
                        Modifier.size(14.dp)
                )


                Column(

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            city.city,

                        fontSize =
                            18.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )


                    Text(

                        text =
                            city.country,

                        fontSize =
                            13.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant,

                        maxLines =
                            1,

                        overflow =
                            TextOverflow.Ellipsis
                    )
                }


                Column(

                    horizontalAlignment =
                        Alignment.End
                ) {

                    Text(

                        text =
                            if (isDay)
                                "☀️ Day"
                            else
                                "🌙 Night",

                        fontSize =
                            13.sp,

                        fontWeight =
                            FontWeight.Medium
                    )


                    Text(

                        text =
                            gmtOffset,

                        fontSize =
                            12.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .primary
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )


            Box(

                modifier =
                    Modifier.fillMaxWidth(),

                contentAlignment =
                    Alignment.Center
            ) {


                if (isAnalog) {

                    AnalogClock(

                        currentTimeMillis =
                            currentTimeMillis,

                        timezone =
                            city.timezone
                    )

                } else {

                    Text(

                        text =
                            currentTime,

                        fontSize =
                            42.sp,

                        fontWeight =
                            FontWeight.Light
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )


            if (isAnalog) {

                Text(

                    text =
                        currentTime,

                    modifier =
                        Modifier.fillMaxWidth(),

                    fontSize =
                        16.sp,

                    fontWeight =
                        FontWeight.Medium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }


            Text(

                text =
                    city.timezone,

                fontSize =
                    12.sp,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}


/*
 * ============================================================
 * PREVIEW
 * ============================================================
 */

@Preview(
    showBackground = true
)
@Composable
fun WorldClockPreview() {

    WorldClockTheme {

        WorldClockApp(

            locationName =
                "Jakarta",

            countryName =
                "Indonesia",

            timezone =
                "Asia/Jakarta",

            onRefreshLocation = {}
        )
    }
}