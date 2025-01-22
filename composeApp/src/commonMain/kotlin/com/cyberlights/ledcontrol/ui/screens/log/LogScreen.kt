package com.cyberlights.ledcontrol.ui.screens.log

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyberlights.ledcontrol.data.models.BleLogEntry
import com.cyberlights.ledcontrol.data.models.LogType
import com.cyberlights.ledcontrol.data.models.ProtocolAnalyzer
import com.cyberlights.ledcontrol.data.models.toHexString
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun LogScreen(
    modifier: Modifier = Modifier
) {
    var logs by remember { mutableStateOf(listOf<BleLogEntry>()) }
    var showAnalysis by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { logs = emptyList() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear log",
                    tint = MaterialTheme.colorScheme.error
                )
            }
            
            IconButton(onClick = { showAnalysis = !showAnalysis }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Toggle analysis",
                    tint = if (showAnalysis) MaterialTheme.colorScheme.primary 
                           else MaterialTheme.colorScheme.onSurface
                )
            }
            
            IconButton(onClick = { /* TODO: Save log */ }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save log",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 8.dp)
        ) {
            items(logs) { entry ->
                LogEntry(entry, showAnalysis)
            }
        }
    }
}

@Composable
private fun LogEntry(
    entry: BleLogEntry,
    showAnalysis: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = formatTimestamp(entry.timestamp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
        )
        
        Text(
            text = buildString {
                append(entry.type.prefix)
                append(entry.data.toHexString())
            },
            color = when(entry.type) {
                LogType.SEND -> Color(0xFF4CAF50)
                LogType.RECEIVE -> Color(0xFF2196F3)
            },
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
        )
        
        if (showAnalysis) {
            Text(
                text = "Analysis: ${ProtocolAnalyzer.analyze(entry.data)}",
                color = Color(0xFFFF9800),
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
            )
        }
        
        if (entry.description.isNotEmpty()) {
            Text(
                text = entry.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
            )
        }
        
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val duration = timestamp.milliseconds
    val hours = duration.inWholeHours % 24
    val minutes = duration.inWholeMinutes % 60
    val seconds = duration.inWholeSeconds % 60
    val millis = duration.inWholeMilliseconds % 1000
    return "%02d:%02d:%02d.%03d".format(hours, minutes, seconds, millis)
} 