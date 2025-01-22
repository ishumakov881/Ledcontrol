package com.cyberlights.ledcontrol

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.cyberlights.ledcontrol.navigation.NavRoute
import com.cyberlights.ledcontrol.theme.ThemeSettings
import com.cyberlights.ledcontrol.ui.components.*
import com.cyberlights.ledcontrol.ui.screens.devices.DevicesScreen
import com.cyberlights.ledcontrol.ui.screens.controls.ControlsScreen
import com.cyberlights.ledcontrol.ui.screens.effects.EffectsScreen
import com.cyberlights.ledcontrol.ui.screens.settings.SettingsScreen
import com.cyberlights.ledcontrol.ui.screens.log.LogScreen
import com.cyberlights.ledcontrol.ui.viewmodels.LogViewModel
import com.cyberlights.ledcontrol.ui.viewmodels.DevicesViewModel

@Composable
fun App() {
    var currentRoute: NavRoute by remember { mutableStateOf(NavRoute.Devices) }
    val logViewModel = remember { LogViewModel() }
    val isDarkTheme = ThemeSettings.isDarkTheme
    
    // Get platform scanner
    val bleScanner = remember { getPlatformBleScanner() }
    val devicesViewModel = remember { DevicesViewModel(bleScanner) }
    
    MaterialTheme(
        colorScheme = if (isDarkTheme) {
            darkColorScheme()
        } else {
            lightColorScheme()
        }
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
                    onNavigate = { currentRoute = it }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when (currentRoute) {
                    is NavRoute.Devices -> {
                        val isScanning by devicesViewModel.isScanning.collectAsState()
                        val devices by devicesViewModel.devices.collectAsState()
                        
                        DevicesScreen(
                            isScanning = isScanning,
                            devices = devices,
                            onStartScan = { devicesViewModel.startScan() },
                            onStopScan = { devicesViewModel.stopScan() },
                            onDeviceClick = { device -> 
                                devicesViewModel.connectToDevice(device)
                            }
                        )
                    }
                    is NavRoute.Controls -> ControlsScreen(
                        onNavigate = { currentRoute = it }
                    )
                    is NavRoute.Effects -> EffectsScreen(
                        onNavigate = { currentRoute = it }
                    )
                    is NavRoute.Settings -> SettingsScreen(
                        onNavigate = { currentRoute = it }
                    )
                    is NavRoute.Log -> LogScreen(
                        modifier = Modifier.padding(padding)
                    )
                    else -> {}
                }
            }
        }
    }
}