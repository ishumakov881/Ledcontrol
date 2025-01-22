package com.cyberlights.ledcontrol.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.cyberlights.ledcontrol.data.models.BleDevice

object DeviceIcons {
    fun getDeviceIcon(device: BleDevice): ImageVector {
        val name = device.name.lowercase()
        val manufacturer = device.manufacturerName?.lowercase() ?: ""
        val address = device.address.lowercase()
        
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
            
            // Garmin устройства
            name.contains("fenix") -> Icons.Default.DirectionsRun
            name.contains("forerunner") -> Icons.Default.DirectionsRun
            name.contains("venu") -> Icons.Default.Watch
            name.contains("instinct") -> Icons.Default.Hiking
            manufacturer.contains("garmin") -> Icons.Default.Watch
            
            // MAC-адреса Garmin (первые 3 октета)
            //address.startsWith("00:1A:11") || // TTPCom Limited (не Garmin!)
            //address.startsWith("00:04:5B") || // Garmin
            address.startsWith("00:1B:C5") -> Icons.Default.Watch // Garmin
            
            // Неизвестные устройства
            else -> Icons.Default.BluetoothSearching
        }
    }
} 