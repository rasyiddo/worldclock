package com.example.worldclock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCityScreen(
    onBack: () -> Unit,
    onCitySelected: (ClockCity) -> Unit
) {

    /*
     * =====================================================
     * SEARCH TEXT
     * =====================================================
     */
    var searchText by remember {
        mutableStateOf("")
    }


    /*
     * =====================================================
     * SEARCH RESULTS
     * =====================================================
     */
    var searchResults by remember {
        mutableStateOf<List<GeoNameResult>>(emptyList())
    }


    /*
     * =====================================================
     * LOADING SEARCH
     * =====================================================
     */
    var isLoading by remember {
        mutableStateOf(false)
    }


    /*
     * =====================================================
     * LOADING TIMEZONE
     * =====================================================
     */
    var isLoadingTimezone by remember {
        mutableStateOf(false)
    }


    /*
     * =====================================================
     * ERROR MESSAGE
     * =====================================================
     */
    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }


    /*
     * =====================================================
     * COROUTINE
     * =====================================================
     */
    val scope = rememberCoroutineScope()


    /*
     * =====================================================
     * SEARCH CITY OTOMATIS
     * =====================================================
     *
     * User mengetik minimal 2 karakter.
     *
     * Kita beri delay 500 ms supaya API
     * tidak dipanggil setiap huruf secara langsung.
     */
    LaunchedEffect(searchText) {

        val query =
            searchText.trim()


        /*
         * Kalau kurang dari 2 karakter,
         * kosongkan hasil.
         */
        if (query.length < 2) {

            searchResults =
                emptyList()

            errorMessage =
                null

            isLoading =
                false

            return@LaunchedEffect
        }


        /*
         * Tunggu sebentar
         * sebelum memanggil API.
         */
        delay(500)


        isLoading =
            true

        errorMessage =
            null


        try {

            /*
             * =================================================
             * PANGGIL GEONAMES SEARCH API
             * =================================================
             */
            val response =
                GeoNamesService.api.searchCities(

                    query = query,

                    maxRows = 20,

                    featureClass = "P",

                    orderBy = "population",

                    style = "FULL",

                    username =
                        GeoNamesConfig.USERNAME
                )


            /*
             * Simpan hasil search.
             */
            searchResults =
                response.geonames


            /*
             * Kalau tidak ada hasil.
             */
            if (response.geonames.isEmpty()) {

                errorMessage =
                    "No cities found."
            }

        } catch (
            e: Exception
        ) {

            searchResults =
                emptyList()

            errorMessage =
                "${e.javaClass.simpleName}: ${e.message}"

        } finally {

            isLoading =
                false
        }
    }


    /*
     * =====================================================
     * SCREEN
     * =====================================================
     */
    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Search City"
                    )
                },


                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "Back"
                        )
                    }
                }
            )
        }

    ) { innerPadding ->


        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
        ) {


            /*
             * =================================================
             * SEARCH BOX
             * =================================================
             */
            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            OutlinedTextField(

                value =
                    searchText,


                onValueChange = {
                    searchText = it
                },


                modifier =
                    Modifier.fillMaxWidth(),


                placeholder = {
                    Text(
                        text =
                            "Search city or country"
                    )
                },


                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Search"
                    )
                },


                singleLine = true
            )


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            /*
             * =================================================
             * SEARCH LOADING
             * =================================================
             */
            if (isLoading) {

                Column(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )


                    CircularProgressIndicator()


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    Text(
                        text =
                            "Searching cities..."
                    )
                }
            }


            /*
             * =================================================
             * TIMEZONE LOADING
             * =================================================
             */
            else if (isLoadingTimezone) {

                Column(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )


                    CircularProgressIndicator()


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    Text(
                        text =
                            "Getting timezone..."
                    )
                }
            }


            /*
             * =================================================
             * ERROR
             * =================================================
             */
            else if (errorMessage != null) {

                Column(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(30.dp)
                    )


                    Text(
                        text =
                            errorMessage ?: "",

                        fontSize =
                            15.sp,

                        color =
                            MaterialTheme.colorScheme.error
                    )
                }
            }


            /*
             * =================================================
             * EMPTY STATE
             * =================================================
             */
            else if (
                searchText.trim().length < 2
            ) {

                Column(

                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(30.dp)
                    )


                    Text(
                        text =
                            "Search for a city",

                        fontSize =
                            18.sp,

                        fontWeight =
                            FontWeight.SemiBold
                    )


                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )


                    Text(
                        text =
                            "Type at least 2 characters",

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }


            /*
             * =================================================
             * SEARCH RESULT
             * =================================================
             */
            else {

                LazyColumn(

                    modifier =
                        Modifier.fillMaxSize(),

                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    items(

                        items =
                            searchResults,

                        /*
                         * Gunakan GeoNames ID sebagai key.
                         *
                         * BUKAN timezone.
                         */
                        key = {
                            it.geonameId
                        }

                    ) { result ->


                        GeoNameSearchItem(

                            result =
                                result,

                            onClick = {

                                /*
                                 * Jalankan coroutine
                                 * untuk mengambil timezone.
                                 */
                                scope.launch {

                                    isLoadingTimezone =
                                        true

                                    errorMessage =
                                        null


                                    try {

                                        /*
                                         * Ambil latitude.
                                         */
                                        val latitude =
                                            result.lat.toDouble()


                                        /*
                                         * Ambil longitude.
                                         */
                                        val longitude =
                                            result.lng.toDouble()


                                        /*
                                         * =================================================
                                         * PANGGIL TIMEZONE API
                                         * =================================================
                                         */
                                        val timezoneResponse =
                                            GeoNamesService.api.getTimezone(

                                                latitude =
                                                    latitude,

                                                longitude =
                                                    longitude,

                                                username =
                                                    GeoNamesConfig.USERNAME
                                            )


                                        /*
                                         * Ambil timezone ID.
                                         */
                                        val timezoneId =
                                            timezoneResponse.timezoneId


                                        /*
                                         * Pastikan timezone tersedia.
                                         */
                                        if (
                                            timezoneId.isBlank()
                                        ) {

                                            throw IllegalStateException(
                                                "Timezone not found"
                                            )
                                        }


                                        /*
                                         * =================================================
                                         * BUAT CLOCK CITY
                                         * =================================================
                                         *
                                         * BAGIAN PALING PENTING:
                                         *
                                         * id = geonameId
                                         *
                                         * Jadi setiap kota memiliki
                                         * identitas unik.
                                         */
                                        val city =
                                            ClockCity(

                                                city =
                                                    result.name,

                                                country =
                                                    result.countryName,

                                                flag =
                                                    countryCodeToFlag(
                                                        result.countryCode
                                                    ),

                                                timezone =
                                                    timezoneId,

                                                id =
                                                    result.geonameId
                                                        .toString()
                                            )


                                        /*
                                         * Kirim kota ke MainActivity.
                                         */
                                        onCitySelected(
                                            city
                                        )

                                    } catch (
                                        e: Exception
                                    ) {

                                        errorMessage =
                                            "Failed to get timezone: " +
                                                    "${e.javaClass.simpleName}: " +
                                                    "${e.message}"

                                    } finally {

                                        isLoadingTimezone =
                                            false
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun GeoNameSearchItem(
    result: GeoNameResult,
    onClick: () -> Unit
) {

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },


        shape =
            MaterialTheme.shapes.large,


        colors =
            CardDefaults.cardColors(

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface
            )
    ) {


        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
        ) {


            /*
             * =================================================
             * CITY
             * =================================================
             */
            Text(

                text =
                    result.name,

                fontSize =
                    18.sp,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            /*
             * =================================================
             * COUNTRY
             * =================================================
             */
            Text(

                text =
                    "${countryCodeToFlag(result.countryCode)} " +
                            result.countryName,

                fontSize =
                    14.sp,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            /*
             * =================================================
             * LOCATION INFO
             * =================================================
             */
            Text(

                text =
                    "Population: ${result.population}",

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