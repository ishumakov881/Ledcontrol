package com.cyberlights.ledcontrol

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.cyberlights.ledcontrol.navigation.NavRoute
import com.cyberlights.ledcontrol.theme.ThemeSettings
import com.cyberlights.ledcontrol.ui.components.*
import com.cyberlights.ledcontrol.ui.screens.devices.DevicesScreen
import com.cyberlights.ledcontrol.ui.screens.controls.ControlsScreen
import com.cyberlights.ledcontrol.ui.screens.effects.EffectsScreen
import com.cyberlights.ledcontrol.ui.screens.settings.SettingsScreen

@Composable
fun App() {
    var currentRoute by remember { mutableStateOf<NavRoute>(NavRoute.Devices) }
    val isDarkTheme = ThemeSettings.isDarkTheme

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentRoute = currentRoute,
                    onBackClick = { }
                )
            },
            bottomBar = {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onRouteSelected = { currentRoute = it }
                )
            }
        ) { padding ->
            when (currentRoute) {
                is NavRoute.Devices -> DevicesScreen(
                    onNavigate = { currentRoute = it }
                )
                is NavRoute.Controls -> ControlsScreen(
                    onNavigate = { currentRoute = it }
                )
                is NavRoute.Effects -> EffectsScreen(
                    onNavigate = { currentRoute = it }
                )
                is NavRoute.Settings -> SettingsScreen(
                    onNavigate = { currentRoute = it }
                )
                else -> {}
            }
        }
    }
}