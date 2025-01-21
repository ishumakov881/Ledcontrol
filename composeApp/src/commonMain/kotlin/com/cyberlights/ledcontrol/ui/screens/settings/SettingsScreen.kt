package com.cyberlights.ledcontrol.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyberlights.ledcontrol.navigation.NavRoute
import com.cyberlights.ledcontrol.theme.ThemeSettings

@Composable
fun SettingsScreen(
    onNavigate: (NavRoute) -> Unit
) {
    var autoConnect by remember { mutableStateOf(false) }
    var darkTheme by remember { mutableStateOf(ThemeSettings.isDarkTheme) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            Text(
                "Device Settings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        item {
            ListItem(
                headlineContent = { Text("Auto-connect") },
                supportingContent = { Text("Connect to last device on startup") },
                trailingContent = { 
                    Switch(
                        checked = autoConnect,
                        onCheckedChange = { autoConnect = it }
                    )
                }
            )
        }
        
        item {
            ListItem(
                headlineContent = { Text("Dark Theme") },
                supportingContent = { Text("Enable dark mode") },
                trailingContent = { 
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = { 
                            darkTheme = it
                            ThemeSettings.isDarkTheme = it
                        }
                    )
                }
            )
        }
    }
} 