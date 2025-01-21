package com.cyberlights.ledcontrol.ui.screens.effects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cyberlights.ledcontrol.navigation.NavRoute

data class Effect(
    val name: String,
    val icon: ImageVector
)

private fun getDemoEffects() = listOf(
    Effect("Rainbow", Icons.Default.ColorLens),
    Effect("Strobe", Icons.Default.FlashOn),
    Effect("Pulse", Icons.Default.Waves),
    Effect("Fade", Icons.Default.Opacity),
    Effect("Static", Icons.Default.Stop)
)

@Composable
fun EffectsScreen(
    onNavigate: (NavRoute) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(getDemoEffects()) { effect ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                onClick = { }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(effect.icon, null)
                    Spacer(Modifier.width(16.dp))
                    Text(effect.name)
                }
            }
        }
    }
} 