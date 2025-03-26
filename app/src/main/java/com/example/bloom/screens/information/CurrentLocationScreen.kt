package com.example.bloom.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

@Composable
fun CurrentLocationScreen(
    uiState: InformationUiState,
    onLocationChange: (String) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(19.0760, 72.8777), 14f) // Default: Mumbai
    }

    var areaName by remember { mutableStateOf("Fetching...") }
    var cityName by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("") }

    // Marker state must be remembered outside LaunchedEffect
    val markerState = rememberMarkerState(position = cameraPositionState.position.target)

    // Fetch area when the camera stops moving
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            val latLng = cameraPositionState.position.target
            getAreaNameFromLatLng(latLng) { area, city, country ->
                areaName = area
                cityName = city
                countryName = country
                onLocationChange(area)

                // Update marker position (since it's a MutableState, Compose will recompute)
                markerState.position = latLng
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Where do you live?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = "Only the neighborhood name will appear on your profile.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card (modifier = Modifier.fillMaxWidth().height(450.dp)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = false)
            ) {
                Marker(
                    state = markerState,
                    title = areaName,  // Displays sub-locality name
                    snippet = "$cityName, $countryName"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Area: $areaName", style = MaterialTheme.typography.titleMedium)
    }
}

// Function to fetch the sub-locality using OpenStreetMap API
fun getAreaNameFromLatLng(latLng: LatLng, callback: (String, String, String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url =
                "https://nominatim.openstreetmap.org/reverse?format=json&lat=${latLng.latitude}&lon=${latLng.longitude}"
            val response = URL(url).readText()
            val jsonObject = JSONObject(response)

            val addressObject = jsonObject.optJSONObject("address")

            val area = addressObject?.optString("suburb", "Unknown Area") ?: "Unknown Area"
            val city = addressObject?.optString("city", "Unknown City") ?: "Unknown City"
            val country = addressObject?.optString("country", "Unknown Country") ?: "Unknown Country"

            withContext(Dispatchers.Main) {
                callback(area, city, country)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                callback("Error fetching area", "Unknown City", "Unknown Country")
            }
        }
    }
}
