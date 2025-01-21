package com.cyberlights.ledcontrol.ui.screens.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cyberlights.ledcontrol.navigation.NavRoute

@Composable
fun ControlsScreen(
    onNavigate: (NavRoute) -> Unit
) {
    var currentColor by remember { mutableStateOf(Color.Red) }
    var brightness by remember { mutableStateOf(1f) }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Color circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(currentColor, CircleShape)
        )
        
        Spacer(Modifier.height(16.dp))
        
        // Brightness slider
        Slider(
            value = brightness,
            onValueChange = { brightness = it },
            modifier = Modifier.fillMaxWidth()
        )
        
        Text("Brightness: ${(brightness * 100).toInt()}%")
    }
} 