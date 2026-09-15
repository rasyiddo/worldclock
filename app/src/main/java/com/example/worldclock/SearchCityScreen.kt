package com.example.worldclock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
     * FILTER CITY
     * =====================================================
     */

    val filteredCities = remember(searchText) {

        if (
            searchText.isBlank()
        ) {

            cityCatalog

        } else {

            cityCatalog.filter { city ->

                city.city.contains(
                    searchText,
                    ignoreCase = true
                ) ||

                        city.country.contains(
                            searchText,
                            ignoreCase = true
                        )
            }
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
                        "Search City"
                    )
                },

                navigationIcon = {

                    IconButton(

                        onClick =
                            onBack

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
                    .padding(
                        horizontal = 16.dp
                    )
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
             * NO RESULT
             * =================================================
             */

            if (
                filteredCities.isEmpty()
            ) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Spacer(
                        modifier =
                            Modifier.height(40.dp)
                    )


                    Text(

                        text =
                            "No cities found",

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
                            "Try another city or country",

                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }


            } else {


                /*
                 * =================================================
                 * CITY LIST
                 * =================================================
                 */

                LazyColumn(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            10.dp
                        )
                ) {

                    items(

                        items =
                            filteredCities,

                        /*
                         * IMPORTANT:
                         *
                         * Jangan gunakan:
                         *
                         * key = { it.timezone }
                         *
                         * Karena banyak kota bisa
                         * menggunakan timezone yang sama.
                         */

                        key = {

                            "${it.country}_${it.city}_${it.timezone}"
                        }

                    ) { city ->

                        CitySearchItem(

                            city =
                                city,

                            onClick = {

                                onCitySelected(
                                    city
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


/*
 * =========================================================
 * CITY SEARCH ITEM
 * =========================================================
 */

@Composable
fun CitySearchItem(
    city: ClockCity,
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

        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

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
                    Modifier.size(14.dp)
            )


            /*
             * CITY INFO
             */

            Column(

                modifier =
                    Modifier.weight(1f)
            ) {

                Text(

                    text =
                        city.city,

                    fontSize =
                        17.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )


                Spacer(
                    modifier =
                        Modifier.height(2.dp)
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


                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )


                Text(

                    text =
                        city.timezone,

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