package com.cyberlights.ledcontrol.ui.screens.devices

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cyberlights.ledcontrol.data.models.BleDevice
import com.cyberlights.ledcontrol.data.models.ConnectionState
import com.cyberlights.ledcontrol.navigation.NavRoute
import com.cyberlights.ledcontrol.ui.components.ManufacturerDataDialog
import com.cyberlights.ledcontrol.utils.copyToClipboard

@Composable
fun DevicesScreen(
    isScanning: Boolean,
    devices: List<BleDevice>,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onDeviceClick: (BleDevice) -> Unit
) {
    var selectedDevice by remember { mutableStateOf<BleDevice?>(null) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Scan button with rotation animation
        val rotation = remember { Animatable(0f) }
        
        LaunchedEffect(isScanning) {
            if (isScanning) {
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            } else {
                rotation.snapTo(0f)
            }
        }

        // Scan button in Card for better visibility
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { if (isScanning) onStopScan() else onStartScan() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = if (isScanning) "Stop Scan" else "Start Scan",
                        modifier = Modifier.rotate(rotation.value),
                        tint = if (isScanning) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Text(
                    text = if (isScanning) "Остановить" else "Начать сканирование",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        if (devices.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isScanning) 
                        "Поиск устройств..." 
                    else 
                        "Нажмите кнопку выше чтобы начать поиск",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(devices) { device ->
                    DeviceItem(
                        device = device,
                        onClick = { 
                            onDeviceClick(device)
                        },
                        onShowManufacturerData = {
                            selectedDevice = device
                        },
                        onCopyManufacturerData = { data ->
                            copyToClipboard(data)
                        }
                    )
                }
            }
        }
    }
    
    // Show dialog if device is selected
    selectedDevice?.let { device ->
        ManufacturerDataDialog(
            device = device,
            onDismiss = { selectedDevice = null }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DeviceItem(
    device: BleDevice,
    onClick: () -> Unit,
    onShowManufacturerData: () -> Unit,
    onCopyManufacturerData: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = when(device.connectionState) {
                ConnectionState.CONNECTED -> MaterialTheme.colorScheme.primaryContainer
                ConnectionState.CONNECTING -> MaterialTheme.colorScheme.secondaryContainer
                ConnectionState.DISCONNECTED -> if (device.isBonded) 
                    MaterialTheme.colorScheme.surfaceVariant 
                else 
                    MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левая часть - имя и адрес
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = device.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    when(device.connectionState) {
                        ConnectionState.CONNECTED -> Icon(
                            imageVector = Icons.Default.Bluetooth,
                            contentDescription = "Connected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        ConnectionState.CONNECTING -> CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        else -> {}
                    }
                }
                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (!device.services.isNullOrEmpty()) {
                    Text(
                        text = "Services: ${device.services.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Правая часть - RSSI и статус
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = when {
                            device.rssi >= BleDevice.RSSI_MAX -> Icons.Default.NetworkWifi
                            device.rssi >= BleDevice.RSSI_GOOD -> Icons.Default.NetworkWifi2Bar
                            device.rssi >= BleDevice.RSSI_WEAK -> Icons.Default.NetworkWifi1Bar
                            else -> Icons.Default.WifiOff
                        },
                        contentDescription = "Signal strength",
                        tint = when {
                            device.rssi >= BleDevice.RSSI_MAX -> MaterialTheme.colorScheme.primary
                            device.rssi >= BleDevice.RSSI_GOOD -> MaterialTheme.colorScheme.secondary
                            device.rssi >= BleDevice.RSSI_WEAK -> MaterialTheme.colorScheme.tertiary
                            else -> MaterialTheme.colorScheme.error
                        },
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${device.rssi} dBm",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                if (device.isBonded) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = "Bonded",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Paired",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                // Show manufacturer info if available
                if (device.manufacturerData != null || device.manufacturerName != null) {
                    Box(
                        modifier = Modifier.combinedClickable(
                            onClick = { 
                                if (device.manufacturerData != null) {
                                    onShowManufacturerData()
                                }
                            },
                            onLongClick = {
                                device.manufacturerData?.let { data ->
                                    onCopyManufacturerData(data)
                                }
                            }
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            device.manufacturerName?.let { name ->
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            if (device.manufacturerData != null) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Has manufacturer data",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 