package com.example.worldclock

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
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
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.core.app.ActivityCompat

import com.example.worldclock.ui.theme.WorldClockTheme

import kotlinx.coroutines.launch

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

            val fineLocationGranted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true

            val coarseLocationGranted =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] == true

            if (
                fineLocationGranted ||
                coarseLocationGranted
            ) {

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

        super.onCreate(
            savedInstanceState
        )

        setContent {

            WorldClockTheme {

                WorldClockApp(
                    locationName = locationName,
                    countryName = countryName,
                    timezone = detectedTimezone,

                    onRefreshLocation = {
                        requestLocationPermission()
                    }
                )
            }
        }

        requestLocationPermission()
    }


    private fun requestLocationPermission() {

        locationPermissionLauncher.launch(

            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    private fun detectLocation() {

        val locationManager =
            getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager


        val hasFineLocation =
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        val hasCoarseLocation =
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


        if (
            !hasFineLocation &&
            !hasCoarseLocation
        ) {
            return
        }


        var location: Location? = null


        try {

            if (
                locationManager.isProviderEnabled(
                    LocationManager.GPS_PROVIDER
                )
            ) {

                location =
                    locationManager.getLastKnownLocation(
                        LocationManager.GPS_PROVIDER
                    )
            }


            if (
                location == null &&
                locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                )
            ) {

                location =
                    locationManager.getLastKnownLocation(
                        LocationManager.NETWORK_PROVIDER
                    )
            }

        } catch (
            _: SecurityException
        ) {

            return
        }


        if (location != null) {

            getAddressFromLocation(
                location
            )

        } else {

            locationName =
                "Location unavailable"

            countryName =
                "Please try again"
        }
    }


    private fun getAddressFromLocation(
        location: Location
    ) {

        try {

            val geocoder =
                Geocoder(this)

            @Suppress("DEPRECATION")
            val addresses: List<Address> =
                geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                ) ?: emptyList()


            if (addresses.isNotEmpty()) {

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
            }

        } catch (
            _: Exception
        ) {

            locationName =
                "Unknown location"

            countryName =
                "Unknown country"
        }
    }
}


/*
 * =========================================================
 * DEFAULT WORLD CITIES
 * =========================================================
 */

val defaultWorldCities = listOf(

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


/*
 * =========================================================
 * WORLD CLOCK APP
 * =========================================================
 */

@Composable
fun WorldClockApp(
    locationName: String,
    countryName: String,
    timezone: String,
    onRefreshLocation: () -> Unit
) {

    val context =
        androidx.compose.ui.platform.LocalContext.current


    val database =
        remember {
            AppDatabase.getInstance(context)
        }


    val favoriteDao =
        database.favoriteCityDao()

    val savedCityDao =
        database.savedCityDao()

    val scope =
        rememberCoroutineScope()


    /*
     * =====================================================
     * FAVORITE STATE
     * =====================================================
     */

    var favoriteIds by remember {

        mutableStateOf(
            setOf<String>()
        )
    }


    var favoriteCityList by remember {

        mutableStateOf(
            listOf<ClockCity>()
        )
    }


    /*
     * =====================================================
     * LOAD FAVORITES
     * =====================================================
     */

    LaunchedEffect(Unit) {

        favoriteDao
            .getAllFavorites()
            .collect { favorites ->

                /*
                 * ID favorite.
                 *
                 * Digunakan untuk menentukan
                 * apakah icon ⭐ aktif atau tidak.
                 */
                favoriteIds =
                    favorites
                        .map {
                            it.id
                        }
                        .toSet()


                /*
                 * Data lengkap favorite.
                 *
                 * Favorites berdiri sendiri dari All Cities.
                 */
                favoriteCityList =
                    favorites.map { favorite ->

                        ClockCity(

                            city =
                                favorite.city,

                            country =
                                favorite.country,

                            flag =
                                favorite.flag,

                            timezone =
                                favorite.timezone,

                            id =
                                favorite.id
                        )
                    }
            }
    }


    /*
     * =====================================================
     * SEARCH SCREEN
     * =====================================================
     */

    var showSearchScreen by remember {

        mutableStateOf(false)
    }


    /*
     * =====================================================
     * ALL CITIES
     * =====================================================
     */

    var selectedCities by remember {

        mutableStateOf(
            emptyList<ClockCity>()
        )
    }


    LaunchedEffect(Unit) {

        savedCityDao
            .getAllSavedCities()
            .collect { savedCities ->

                if (
                    savedCities.isEmpty()
                ) {

                    /*
                     * Kalau belum ada kota,
                     * masukkan default cities.
                     */

                    defaultWorldCities.forEach { city ->

                        savedCityDao.insertSavedCity(

                            SavedCity(

                                timezone =
                                    city.timezone,

                                city =
                                    city.city,

                                country =
                                    city.country,

                                flag =
                                    city.flag,

                                id =
                                    city.id
                            )
                        )
                    }

                } else {

                    selectedCities =
                        savedCities.map { savedCity ->

                            ClockCity(

                                city =
                                    savedCity.city,

                                country =
                                    savedCity.country,

                                flag =
                                    savedCity.flag,

                                timezone =
                                    savedCity.timezone,

                                id =
                                    savedCity.id
                            )
                        }
                }
            }
    }


    /*
     * =====================================================
     * SCREEN SWITCH
     * =====================================================
     */

    if (showSearchScreen) {

        SearchCityScreen(

            onBack = {

                showSearchScreen =
                    false
            },


            onCitySelected = { city ->

                /*
                 * Gunakan ID kota.
                 *
                 * Jadi dua kota berbeda dengan
                 * timezone sama tetap bisa masuk.
                 */

                val alreadyExists =
                    selectedCities.any {

                        it.id ==
                                city.id
                    }


                if (!alreadyExists) {

                    scope.launch {

                        savedCityDao.insertSavedCity(

                            SavedCity(

                                timezone =
                                    city.timezone,

                                city =
                                    city.city,

                                country =
                                    city.country,

                                flag =
                                    city.flag,

                                id =
                                    city.id
                            )
                        )
                    }
                }


                showSearchScreen =
                    false
            }
        )

    } else {

        /*
         * =================================================
         * HOME
         * =================================================
         */

        WorldClockHomeScreen(

            locationName =
                locationName,

            countryName =
                countryName,

            timezone =
                timezone,

            cities =
                selectedCities,

            favoriteCities =
                favoriteIds,

            favoriteCityList =
                favoriteCityList,


            /*
             * =============================================
             * FAVORITE
             * =============================================
             */

            onToggleFavorite = { city ->

                val isFavorite =
                    favoriteIds.contains(
                        city.id
                    )


                scope.launch {

                    if (isFavorite) {

                        /*
                         * HANYA hapus favorite.
                         *
                         * All Cities tidak disentuh.
                         */

                        favoriteDao.deleteFavorite(
                            city.id
                        )

                    } else {

                        /*
                         * Tambahkan favorite.
                         */

                        favoriteDao.insertFavorite(

                            FavoriteCity(

                                timezone =
                                    city.timezone,

                                city =
                                    city.city,

                                country =
                                    city.country,

                                flag =
                                    city.flag,

                                id =
                                    city.id
                            )
                        )
                    }
                }
            },


            /*
             * =============================================
             * REFRESH LOCATION
             * =============================================
             */

            onRefreshLocation = {

                onRefreshLocation()
            },


            /*
             * =============================================
             * ADD CITY
             * =============================================
             */

            onAddCity = {

                showSearchScreen =
                    true
            },


            /*
             * =============================================
             * DELETE CITY FROM ALL
             * =============================================
             *
             * PENTING:
             *
             * DI SINI KITA TIDAK BOLEH
             * memanggil favoriteDao.deleteFavorite()
             *
             * Jadi ketika kota dihapus dari
             * All Cities, favorite tetap aman.
             */

            onDeleteCity = { city ->

                scope.launch {

                    savedCityDao.deleteSavedCity(
                        city.id
                    )
                }
            }
        )
    }
}


/*
 * =========================================================
 * HOME SCREEN
 * =========================================================
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldClockHomeScreen(
    locationName: String,
    countryName: String,
    timezone: String,
    cities: List<ClockCity>,
    favoriteCities: Set<String>,
    favoriteCityList: List<ClockCity>,
    onToggleFavorite: (ClockCity) -> Unit,
    onRefreshLocation: () -> Unit,
    onAddCity: () -> Unit,
    onDeleteCity: (ClockCity) -> Unit
) {

    /*
     * Waktu sekarang.
     */

    var currentTimeMillis by remember {

        mutableStateOf(
            System.currentTimeMillis()
        )
    }


    /*
     * false = Digital
     * true = Analog
     */

    var isAnalog by remember {

        mutableStateOf(false)
    }


    /*
     * 0 = All Cities
     * 1 = Favorites
     */

    var selectedTab by remember {

        mutableStateOf(0)
    }


    /*
     * Update setiap 1 detik.
     */

    LaunchedEffect(Unit) {

        while (true) {

            currentTimeMillis =
                System.currentTimeMillis()

            kotlinx.coroutines.delay(1000)
        }
    }


    /*
     * =====================================================
     * VISIBLE CITIES
     * =====================================================
     *
     * All Cities:
     *     berasal dari saved cities
     *
     * Favorites:
     *     berasal langsung dari favorite database
     *
     * Jadi Favorite TIDAK bergantung
     * pada All Cities.
     */

    val visibleCities =

        if (
            selectedTab == 0
        ) {

            cities

        } else {

            favoriteCityList
        }


    /*
     * =====================================================
     * SCAFFOLD
     * =====================================================
     */

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "World Clock",
                            fontWeight =
                                FontWeight.Bold
                        )

                        Text(
                            text =
                                "Your time around the world",

                            fontSize =
                                12.sp,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )
                    }
                },


                actions = {

                    IconButton(

                        onClick =
                            onRefreshLocation

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Refresh,

                            contentDescription =
                                "Refresh location",

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                        )
                    }
                }
            )
        },


        floatingActionButton = {

            FloatingActionButton(

                onClick =
                    onAddCity
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
                    .padding(
                        horizontal = 16.dp
                    ),

            verticalArrangement =
                Arrangement.spacedBy(
                    14.dp
                )
        ) {


            /*
             * =================================================
             * LOCAL TIME
             * =================================================
             */

            item {

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                LocalTimeCard(

                    locationName =
                        locationName,

                    countryName =
                        countryName,

                    timezone =
                        timezone,

                    currentTimeMillis =
                        currentTimeMillis
                )
            }


            /*
             * =================================================
             * WORLD CITIES HEADER
             * =================================================
             */

            item {

                Row(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    Text(

                        text =
                            "World Cities",

                        fontSize =
                            22.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    /*
                     * =========================================
                     * CLOCK MODE SELECTOR
                     * =========================================
                     *
                     * Sebelumnya:
                     *
                     * Digital = LightMode
                     * Analog  = DarkMode
                     *
                     * Itu salah secara visual.
                     *
                     * Sekarang:
                     *
                     * Digital = "digital"
                     * Analog  = "analog"
                     */

                    Row {

                        /*
                         * DIGITAL
                         */

                        IconButton(

                            onClick = {

                                isAnalog =
                                    false
                            }

                        ) {

                            Text(

                                text =
                                    "12:12",

                                fontSize =
                                    13.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =

                                    if (!isAnalog) {

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    } else {

                                        MaterialTheme
                                            .colorScheme
                                            .onSurfaceVariant
                                    }
                            )
                        }


                        /*
                         * ANALOG
                         */

                        IconButton(

                            onClick = {

                                isAnalog =
                                    true
                            }

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.AccessTime,

                                contentDescription =
                                    "Analog clock",

                                tint =

                                    if (isAnalog) {

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    } else {

                                        MaterialTheme
                                            .colorScheme
                                            .onSurfaceVariant
                                    }
                            )
                        }
                    }
                }
            }


            /*
             * =================================================
             * ALL / FAVORITES
             * =================================================
             */

            item {

                TabRow(

                    selectedTabIndex =
                        selectedTab
                ) {

                    Tab(

                        selected =
                            selectedTab == 0,

                        onClick = {

                            selectedTab =
                                0
                        },

                        text = {

                            Text(
                                text =
                                    "All Cities"
                            )
                        }
                    )


                    Tab(

                        selected =
                            selectedTab == 1,

                        onClick = {

                            selectedTab =
                                1
                        },

                        text = {

                            Text(
                                text =
                                    "Favorites"
                            )
                        }
                    )
                }
            }


            /*
             * =================================================
             * CITY LIST
             * =================================================
             */

            if (
                visibleCities.isEmpty()
            ) {

                item {

                    EmptyFavoritesState()
                }

            } else {

                items(

                    items =
                        visibleCities,

                    /*
                     * Jangan timezone.
                     *
                     * Gunakan ID karena dua kota
                     * bisa mempunyai timezone sama.
                     */

                    key = {
                        it.id
                    }

                ) { city ->


                    CityClockCard(

                        city =
                            city,

                        currentTimeMillis =
                            currentTimeMillis,

                        isAnalog =
                            isAnalog,

                        isFavorite =
                            favoriteCities.contains(
                                city.id
                            ),


                        /*
                         * Favorite bisa diubah
                         * dari All maupun Favorites.
                         */

                        onToggleFavorite = {

                            onToggleFavorite(
                                city
                            )
                        },


                        /*
                         * DELETE hanya ditampilkan
                         * di All Cities.
                         *
                         * Jadi user tidak bingung:
                         * Favorites bukan tempat
                         * untuk menghapus city.
                         */

                        showDelete =
                            selectedTab == 0,

                        onDeleteCity = {

                            onDeleteCity(
                                city
                            )
                        }
                    )
                }
            }


            /*
             * =================================================
             * BOTTOM SPACE
             * =================================================
             */

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
 * =========================================================
 * LOCAL TIME CARD
 * =========================================================
 */

@Composable
fun LocalTimeCard(
    locationName: String,
    countryName: String,
    timezone: String,
    currentTimeMillis: Long
) {

    val time =
        getCurrentTime(
            currentTimeMillis,
            timezone
        )


    val offset =
        getGmtOffset(
            currentTimeMillis,
            timezone
        )


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

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
                Modifier.padding(20.dp)
        ) {

            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(

                    imageVector =
                        Icons.Default.MyLocation,

                    contentDescription =
                        "Current location"
                )


                Spacer(

                    modifier =
                        Modifier.width(8.dp)
                )


                Text(

                    text =
                        "Your Location",

                    fontWeight =
                        FontWeight.Bold
                )
            }


            Spacer(

                modifier =
                    Modifier.height(12.dp)
            )


            Text(

                text =
                    locationName,

                fontSize =
                    25.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Text(

                text =
                    countryName,

                color =
                    MaterialTheme
                        .colorScheme
                        .onPrimaryContainer
            )


            Spacer(

                modifier =
                    Modifier.height(12.dp)
            )


            Text(

                text =
                    time,

                fontSize =
                    40.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Text(

                text =
                    offset,

                color =
                    MaterialTheme
                        .colorScheme
                        .onPrimaryContainer
            )
        }
    }
}


/*
 * =========================================================
 * CITY CLOCK CARD
 * =========================================================
 */

@Composable
fun CityClockCard(
    city: ClockCity,
    currentTimeMillis: Long,
    isAnalog: Boolean,
    isFavorite: Boolean,
    showDelete: Boolean,
    onToggleFavorite: () -> Unit,
    onDeleteCity: () -> Unit
) {

    val time =
        getCurrentTime(
            currentTimeMillis,
            city.timezone
        )


    val offset =
        getGmtOffset(
            currentTimeMillis,
            city.timezone
        )


    val day =
        isDayTime(
            currentTimeMillis,
            city.timezone
        )


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp)
    ) {

        Column(

            modifier =
                Modifier.padding(16.dp)
        ) {


            /*
             * =================================================
             * CITY HEADER
             * =================================================
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {


                /*
                 * FLAG
                 */

                Text(

                    text =
                        city.flag,

                    fontSize =
                        30.sp
                )


                Spacer(

                    modifier =
                        Modifier.width(12.dp)
                )


                /*
                 * CITY NAME
                 */

                Column(

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            city.city,

                        fontSize =
                            19.sp,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Text(

                        text =
                            city.country,

                        fontSize =
                            13.sp,

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }


                /*
                 * =================================================
                 * FAVORITE
                 * =================================================
                 */

                IconButton(

                    onClick =
                        onToggleFavorite

                ) {

                    Icon(

                        imageVector =

                            if (isFavorite) {

                                Icons.Default.Star

                            } else {

                                Icons.Default.StarBorder
                            },

                        contentDescription =

                            if (isFavorite) {

                                "Remove from favorites"

                            } else {

                                "Add to favorites"
                            },

                        tint =

                            if (isFavorite) {

                                MaterialTheme
                                    .colorScheme
                                    .primary

                            } else {

                                MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                            }
                    )
                }


                /*
                 * =================================================
                 * DELETE
                 * =================================================
                 *
                 * Hanya muncul di All Cities.
                 */

                if (showDelete) {

                    IconButton(

                        onClick =
                            onDeleteCity

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Delete,

                            contentDescription =
                                "Delete city",

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                    }
                }
            }


            Spacer(

                modifier =
                    Modifier.height(12.dp)
            )


            /*
             * =================================================
             * CLOCK
             * =================================================
             */

            if (isAnalog) {

                Box(

                    modifier =
                        Modifier.fillMaxWidth(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    AnalogClock(

                        currentTimeMillis =
                            currentTimeMillis,

                        timezone =
                            city.timezone
                    )
                }


                Spacer(

                    modifier =
                        Modifier.height(10.dp)
                )


                Text(

                    text =
                        time,

                    modifier =
                        Modifier.fillMaxWidth(),

                    fontSize =
                        22.sp,

                    fontWeight =
                        FontWeight.Bold
                )

            } else {

                Text(

                    text =
                        time,

                    fontSize =
                        38.sp,

                    fontWeight =
                        FontWeight.Bold
                )
            }


            Spacer(

                modifier =
                    Modifier.height(4.dp)
            )


            /*
             * =================================================
             * DAY / NIGHT + GMT
             * =================================================
             */

            Row(

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(

                    text =

                        if (day) {

                            "☀️ Day"

                        } else {

                            "🌙 Night"
                        },

                    fontSize =
                        13.sp
                )


                Spacer(

                    modifier =
                        Modifier.width(10.dp)
                )


                Text(

                    text =
                        offset,

                    fontSize =
                        13.sp,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }
        }
    }
}


/*
 * =========================================================
 * EMPTY FAVORITES
 * =========================================================
 */

@Composable
fun EmptyFavoritesState() {

    Card(

        modifier =
            Modifier.fillMaxWidth()
    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(30.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(

                imageVector =
                    Icons.Default.StarBorder,

                contentDescription =
                    null,

                modifier =
                    Modifier.size(48.dp),

                tint =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )


            Spacer(

                modifier =
                    Modifier.height(12.dp)
            )


            Text(

                text =
                    "No favorites yet",

                fontSize =
                    18.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(

                modifier =
                    Modifier.height(4.dp)
            )


            Text(

                text =
                    "Tap the star on a city to add it here.",

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}