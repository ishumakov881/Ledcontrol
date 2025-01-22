package com.cyberlights.ledcontrol.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.cyberlights.ledcontrol.data.models.BleCharacteristicInfo
import com.cyberlights.ledcontrol.data.models.BleDevice
import com.cyberlights.ledcontrol.data.models.BleServiceInfo
import com.cyberlights.ledcontrol.data.models.BleServicesDatabase
import com.cyberlights.ledcontrol.data.models.CharacteristicProperty

@Composable
fun ServicesDialog(
    device: BleDevice,
    onDismiss: () -> Unit,
    onCharacteristicRead: ((String, String) -> Unit)? = null,
    onCharacteristicWrite: ((String, String) -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Сервисы устройства",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            if (device.services.isEmpty()) {
                Text(
                    text = "Нет доступных сервисов",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(device.services) { serviceUuid ->
                        val serviceInfo = BleServicesDatabase.getServiceInfo(serviceUuid)
                        ServiceCard(
                            serviceInfo = serviceInfo,
                            onCharacteristicRead = onCharacteristicRead,
                            onCharacteristicWrite = onCharacteristicWrite
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}

@Composable
private fun ServiceCard(
    serviceInfo: BleServiceInfo,
    onCharacteristicRead: ((String, String) -> Unit)?,
    onCharacteristicWrite: ((String, String) -> Unit)?
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Service header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = serviceInfo.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = serviceInfo.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = serviceInfo.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Свернуть" else "Развернуть",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // UUID
            Text(
                text = serviceInfo.uuid,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            
            // Characteristics
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (serviceInfo.characteristics.isEmpty()) {
                        Text(
                            text = "Нет доступных характеристик",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        serviceInfo.characteristics.forEach { characteristic ->
                            CharacteristicItem(
                                characteristic = characteristic,
                                serviceUuid = serviceInfo.uuid,
                                onRead = onCharacteristicRead,
                                onWrite = onCharacteristicWrite
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacteristicItem(
    characteristic: BleCharacteristicInfo,
    serviceUuid: String,
    onRead: ((String, String) -> Unit)?,
    onWrite: ((String, String) -> Unit)?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = characteristic.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = characteristic.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = characteristic.uuid,
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
                
                // Property buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (characteristic.properties.contains(CharacteristicProperty.READ) && onRead != null) {
                        IconButton(
                            onClick = { onRead(serviceUuid, characteristic.uuid) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = "Читать",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    if (characteristic.properties.contains(CharacteristicProperty.WRITE) && onWrite != null) {
                        IconButton(
                            onClick = { onWrite(serviceUuid, characteristic.uuid) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Upload,
                                contentDescription = "Записать",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    
                    if (characteristic.properties.contains(CharacteristicProperty.NOTIFY)) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Уведомления",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
} 