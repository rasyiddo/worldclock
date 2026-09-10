package com.example.worldclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worldclock.ui.theme.WorldClockTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorldClockTheme {
                WorldClockApp()
            }
        }
    }
}


data class ClockCity(
    val city: String,
    val country: String,
    val flag: String,
    val timezone: String
)


@Composable
fun WorldClockApp() {

    // Menyimpan waktu sekarang.
    // Nilai ini akan berubah setiap 1 detik.
    var currentTimeMillis by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }


    // Refresh setiap 1 detik
    LaunchedEffect(Unit) {

        while (true) {

            currentTimeMillis = System.currentTimeMillis()

            delay(1000)
        }
    }


    // Daftar kota sementara
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
                    // Akan digunakan nanti untuk Add City
                },
                shape = CircleShape
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add City"
                )
            }
        }

    ) { innerPadding ->


        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {


            // HEADER
            item {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                TopHeader()
            }


            // YOUR LOCATION
            item {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                YourLocationCard(
                    currentTimeMillis = currentTimeMillis
                )
            }


            // WORLD CLOCK TITLE
            item {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "World clocks",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }


            // WORLD CLOCK LIST
            items(cities) { city ->

                CityClockCard(
                    city = city,
                    currentTimeMillis = currentTimeMillis
                )
            }


            // Bottom spacing
            item {

                Spacer(
                    modifier = Modifier.height(80.dp)
                )
            }
        }
    }
}


@Composable
fun TopHeader() {

    Row(

        modifier = Modifier.fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically
    ) {


        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "WorldClock",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )


            Text(
                text = "Your time, anywhere in the world.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


        IconButton(
            onClick = {
                // Settings akan dibuat nanti
            }
        ) {

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    }
}


@Composable
fun YourLocationCard(
    currentTimeMillis: Long
) {

    // Ambil waktu Jakarta berdasarkan timestamp
    val currentTime = getCurrentTime(
        currentTimeMillis = currentTimeMillis,
        zoneId = "Asia/Jakarta"
    )


    // Ambil GMT offset secara otomatis
    val gmtOffset = getGmtOffset(
        currentTimeMillis = currentTimeMillis,
        zoneId = "Asia/Jakarta"
    )


    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(28.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {


        Column(
            modifier = Modifier.padding(22.dp)
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {


                Box(

                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = 0.15f
                            )
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }


                Spacer(
                    modifier = Modifier.size(12.dp)
                )


                Column {

                    Text(
                        text = "YOUR LOCATION",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary.copy(
                            alpha = 0.75f
                        )
                    )


                    Text(
                        text = "Jakarta, Indonesia",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(22.dp)
            )


            // JAM REALTIME
            Text(
                text = currentTime,
                fontSize = 52.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onPrimary
            )


            // GMT OTOMATIS
            Text(
                text = "$gmtOffset  •  Jakarta",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(
                    alpha = 0.8f
                )
            )
        }
    }
}


@Composable
fun CityClockCard(
    city: ClockCity,
    currentTimeMillis: Long
) {

    // Ambil waktu berdasarkan timezone kota
    val currentTime = getCurrentTime(
        currentTimeMillis = currentTimeMillis,
        zoneId = city.timezone
    )


    // Ambil GMT offset otomatis
    val gmtOffset = getGmtOffset(
        currentTimeMillis = currentTimeMillis,
        zoneId = city.timezone
    )


    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {


        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {


            // FLAG
            Text(
                text = city.flag,
                fontSize = 32.sp
            )


            Spacer(
                modifier = Modifier.size(14.dp)
            )


            // CITY INFORMATION
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = city.city,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )


                Text(
                    text = city.country,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }


            // TIME
            Column(
                horizontalAlignment = Alignment.End
            ) {


                Text(
                    text = currentTime,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )


                Text(
                    text = gmtOffset,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorldClockPreview() {

    WorldClockTheme {

        WorldClockApp()
    }
}