package com.cyberlights.ledcontrol.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.cyberlights.ledcontrol.navigation.NavRoute

@Composable
fun AppBottomBar(
    currentRoute: NavRoute,
    onNavigate: (NavRoute) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute is NavRoute.Devices,
            onClick = { onNavigate(NavRoute.Devices) },
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            label = { Text("Devices") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Controls,
            onClick = { onNavigate(NavRoute.Controls) },
            icon = { Icon(Icons.Default.Edit, contentDescription = null) },
            label = { Text("Controls") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Effects,
            onClick = { onNavigate(NavRoute.Effects) },
            icon = { Icon(Icons.Default.Star, contentDescription = null) },
            label = { Text("Effects") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Settings,
            onClick = { onNavigate(NavRoute.Settings) },
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") }
        )
        NavigationBarItem(
            selected = currentRoute is NavRoute.Log,
            onClick = { onNavigate(NavRoute.Log) },
            icon = { Icon(Icons.Default.Code, contentDescription = null) },
            label = { Text("Log") }
        )
    }
} 