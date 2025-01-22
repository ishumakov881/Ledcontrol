package com.cyberlights.ledcontrol.ui.screens.logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cyberlights.ledcontrol.data.models.BleLogEntry
import com.cyberlights.ledcontrol.data.models.LogType

@Composable
fun LogsScreen(
    logs: List<BleLogEntry>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(logs) { log ->
                LogItem(log = log)
            }
        }
    }
}

@Composable
private fun LogItem(
    log: BleLogEntry
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (log.type) {
                LogType.SEND -> MaterialTheme.colorScheme.primaryContainer
                LogType.RECEIVE -> MaterialTheme.colorScheme.secondaryContainer
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Иконка направления
            Icon(
                imageVector = when (log.type) {
                    LogType.SEND -> Icons.Default.ArrowUpward
                    LogType.RECEIVE -> Icons.Default.ArrowDownward
                },
                contentDescription = log.type.name,
                tint = when (log.type) {
                    LogType.SEND -> MaterialTheme.colorScheme.primary
                    LogType.RECEIVE -> MaterialTheme.colorScheme.secondary
                },
                modifier = Modifier.size(24.dp)
            )
            
            // Данные и описание
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Описание операции
                Text(
                    text = log.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                // Сырые данные в HEX
                if (log.data.isNotEmpty()) {
                    Text(
                        text = log.data.joinToString(" ") { "%02X".format(it) },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
} 