package com.cyberlights.ledcontrol.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.cyberlights.ledcontrol.navigation.NavRoute

@Composable
fun AppBottomBar(
    currentRoute: NavRoute,
    onRouteSelected: (NavRoute) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute is NavRoute.Devices,
            onClick = { onRouteSelected(NavRoute.Devices) },
            icon = { Icon(Icons.Default.Search, null) },
            label = { Text("Devices") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Controls,
            onClick = { onRouteSelected(NavRoute.Controls) },
            icon = { Icon(Icons.Default.Edit, null) },
            label = { Text("Controls") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Effects,
            onClick = { onRouteSelected(NavRoute.Effects) },
            icon = { Icon(Icons.Default.Star, null) },
            label = { Text("Effects") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Settings,
            onClick = { onRouteSelected(NavRoute.Settings) },
            icon = { Icon(Icons.Default.Settings, null) },
            label = { Text("Settings") }
        )
    }
} 