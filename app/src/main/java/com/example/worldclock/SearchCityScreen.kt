package com.example.worldclock

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    var searchText by remember {
        mutableStateOf("")
    }

    var searchResults by remember {
        mutableStateOf<List<GeoNameResult>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isLoadingTimezone by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()

    /*
     * =====================================================
     * SEARCH CITY
     * =====================================================
     */

    LaunchedEffect(searchText) {

        val query = searchText.trim()

        if (query.length < 2) {
            searchResults = emptyList()
            errorMessage = null
            isLoading = false
            return@LaunchedEffect
        }

        delay(500)

        isLoading = true
        errorMessage = null

        try {

            val response = GeoNamesService.api.searchCities(
                query = query,
                maxRows = 20,
                featureClass = "P",
                orderBy = "population",
                style = "FULL",
                username = GeoNamesConfig.USERNAME
            )

            searchResults = response.geonames

            if (response.geonames.isEmpty()) {
                errorMessage = "No cities found."
            }

        } catch (e: Exception) {

            searchResults = emptyList()

            errorMessage =
                "${e.javaClass.simpleName}: ${e.message}"

        } finally {

            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Search City")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search city or country")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            /*
             * =================================================
             * SEARCH LOADING
             * =================================================
             */

            if (isLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()
                }
            }

            /*
             * =================================================
             * TIMEZONE LOADING
             * =================================================
             */

            else if (isLoadingTimezone) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator()

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "Getting timezone..."
                        )
                    }
                }
            }

            /*
             * =================================================
             * ERROR
             * =================================================
             */

            else if (errorMessage != null) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            androidx.compose.material3.MaterialTheme
                                .colorScheme
                                .errorContainer
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Search Error",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = errorMessage!!
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            /*
             * =================================================
             * RESULT
             * =================================================
             */

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = searchResults,
                    key = {
                        it.geonameId
                    }
                ) { result ->

                    GeoNameSearchItem(
                        result = result,
                        onClick = {

                            /*
                             * IMPORTANT:
                             * Get timezone from GeoNames
                             * using latitude + longitude.
                             */

                            scope.launch {

                                isLoadingTimezone = true
                                errorMessage = null

                                try {

                                    val latitude =
                                        result.lat.toDouble()

                                    val longitude =
                                        result.lng.toDouble()

                                    val timezoneResponse =
                                        GeoNamesService.api
                                            .getTimezone(
                                                latitude = latitude,
                                                longitude = longitude,
                                                username =
                                                    GeoNamesConfig
                                                        .USERNAME
                                            )

                                    val timezoneId =
                                        timezoneResponse
                                            .timezoneId

                                    if (
                                        timezoneId.isBlank()
                                    ) {

                                        throw IllegalStateException(
                                            "Timezone not found"
                                        )
                                    }

                                    val city =
                                        ClockCity(
                                            city = result.name,
                                            country =
                                                result.countryName,
                                            flag =
                                                countryCodeToFlag(
                                                    result.countryCode
                                                ),
                                            timezone =
                                                timezoneId
                                        )

                                    onCitySelected(city)

                                } catch (e: Exception) {

                                    errorMessage =
                                        "Failed to get timezone: " +
                                                "${e.javaClass.simpleName}: " +
                                                "${e.message}"

                                } finally {

                                    isLoadingTimezone = false
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

/*
 * =========================================================
 * SEARCH RESULT ITEM
 * =========================================================
 */

@Composable
fun GeoNameSearchItem(
    result: GeoNameResult,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(
            16.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = countryCodeToFlag(
                    result.countryCode
                ),
                fontSize = 30.sp,
                modifier = Modifier.size(42.dp)
            )

            Spacer(
                modifier = Modifier.size(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = result.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = result.countryName,
                    fontSize = 14.sp,
                    color =
                        androidx.compose.material3.MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )

                if (result.population > 0) {

                    Text(
                        text =
                            "Population: ${
                                String.format(
                                    "%,d",
                                    result.population
                                )
                            }",
                        fontSize = 12.sp,
                        color =
                            androidx.compose.material3.MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }
        }
    }
}