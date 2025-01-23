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
import com.cyberlights.ledcontrol.data.BleScanner

import com.cyberlights.ledcontrol.data.models.ConnectionState

data class Effect(
    val name: String,
    val icon: ImageVector,
    val command: ByteArray
)

private fun getDemoEffects() = listOf(
    Effect("Включить", Icons.Default.PowerSettingsNew, byteArrayOf(0x01.toByte())),
    Effect("Выключить", Icons.Default.PowerOff, byteArrayOf(0x00.toByte())),
    Effect("Красный", Icons.Default.Circle, byteArrayOf(0x02.toByte())),
    Effect("Зеленый", Icons.Default.Circle, byteArrayOf(0x03.toByte())),
    Effect("Синий", Icons.Default.Circle, byteArrayOf(0x04.toByte())),
    Effect("Белый", Icons.Default.Circle, byteArrayOf(0x05.toByte())),
    Effect("Радуга", Icons.Default.FlashOn, byteArrayOf(0x06.toByte())),
    Effect("Плавное", Icons.Default.Waves, byteArrayOf(0x07.toByte())),
    Effect("Строб", Icons.Default.Bolt, byteArrayOf(0x08.toByte())),
    Effect("Мерцание", Icons.Default.Opacity, byteArrayOf(0x09.toByte())),
    Effect("Яркость +", Icons.Default.Add, byteArrayOf(0x0A.toByte())),
    Effect("Яркость -", Icons.Default.Remove, byteArrayOf(0x0B.toByte())),
    Effect("Скорость +", Icons.Default.FastForward, byteArrayOf(0x0C.toByte())),
    Effect("Скорость -", Icons.Default.SlowMotionVideo, byteArrayOf(0x0D.toByte()))
)

@Composable
fun EffectsScreen(
    onNavigate: (NavRoute) -> Unit,
    bleScanner: BleScanner
) {
    val devices by bleScanner.devices.collectAsState()
    val connectedDevice = devices.find { it.connectionState == ConnectionState.CONNECTED }

    if (connectedDevice == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Нет подключенного устройства")
        }
        return
    }

//    // Ищем сервис управления
//    val controlService = connectedDevice.services.find {
//        it.endsWith("f371") || it == "0000f371-0000-1000-8000-00805f9b34fb"
//    }

    //if (controlService == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Сервис управления не найден")
        }
        //return
    //}

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(getDemoEffects()) { effect ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                onClick = { 
                    bleScanner.writeCharacteristic(effect.command)
                }
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