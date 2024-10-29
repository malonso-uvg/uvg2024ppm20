package edu.uvg.location

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.LocalContext


@Composable
fun LocationScreen(modifier : Modifier = Modifier.fillMaxSize()) {
    // Estados para almacenar la latitud y longitud
    var latitude by remember { mutableStateOf("Unknown") }
    var longitude by remember { mutableStateOf("Unknown") }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Launchers para pedir permisos
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Si el permiso está otorgado, obtener la ubicación
            getLocation(fusedLocationClient) { lat, lon ->
                latitude = lat
                longitude = lon
            }
        } else {
            latitude = "Permission Denied"
            longitude = "Permission Denied"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                        // Si el permiso ya está otorgado, obtener la ubicación
                        getLocation(fusedLocationClient) { lat, lon ->
                            latitude = lat
                            longitude = lon
                        }
                    }
                    else -> {
                        // Si el permiso no está otorgado, pedirlo
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
        ) {
            Text("Obtener Ubicación")
        }

        Text(text = "Latitud: $latitude")
        Text(text = "Longitud: $longitude")
    }
}

@SuppressLint("MissingPermission")
private fun getLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (String, String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onLocationResult(location.latitude.toString(), location.longitude.toString())
        } else {
            onLocationResult("No location found", "No location found")
        }
    }
}