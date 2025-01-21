package com.cyberlights.ledcontrol.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.cyberlights.ledcontrol.navigation.NavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentRoute: NavRoute,
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(getScreenTitle(currentRoute)) },
        actions = {
            Icon(
                imageVector = Icons.Default.Bluetooth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null
                )
            }
        }
    )
}

private fun getScreenTitle(route: NavRoute): String = when(route) {
    is NavRoute.Devices -> "Devices"
    is NavRoute.Controls -> "Controls" 
    is NavRoute.Effects -> "Effects"
    is NavRoute.Settings -> "Settings"
    else -> {"Devices"}
}