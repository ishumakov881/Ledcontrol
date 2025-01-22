package com.cyberlights.ledcontrol.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

object DeviceIcons {
    fun getDeviceIcon(deviceName: String, manufacturerName: String?): ImageVector {
        val name = deviceName.lowercase()
        val manufacturer = manufacturerName?.lowercase() ?: ""
        
        return when {
            // Умные лампы
            name.contains("led") || name.contains("light") || name.contains("lamp") -> Icons.Default.LightbulbCircle
            
            // Фитнес трекеры и часы
            name.contains("mi band") || name.contains("watch") || manufacturer.contains("huami") -> Icons.Default.Watch
            
            // Xiaomi устройства
            manufacturer.contains("xiaomi") -> Icons.Default.Devices
            
            // Apple устройства
            manufacturer.contains("apple") -> Icons.Default.Smartphone
            
            // Термометры и датчики
            name.contains("temp") || name.contains("therm") || name.contains("sensor") -> Icons.Default.DeviceThermostat
            
            // Bluetooth аудио
            name.contains("speaker") || name.contains("headphone") || name.contains("audio") -> Icons.Default.Headphones
            
            // Неизвестные устройства
            else -> Icons.Default.BluetoothSearching
        }
    }
} 