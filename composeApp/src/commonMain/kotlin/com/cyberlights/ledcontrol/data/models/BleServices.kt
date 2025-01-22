package com.cyberlights.ledcontrol.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class BleServiceInfo(
    val uuid: String,
    val name: String,
    val description: String,
    val icon: ImageVector,
    val characteristics: List<BleCharacteristicInfo> = emptyList()
)

data class BleCharacteristicInfo(
    val uuid: String,
    val name: String,
    val description: String,
    val properties: Set<CharacteristicProperty> = emptySet()
)

enum class CharacteristicProperty {
    READ,
    WRITE,
    NOTIFY,
    INDICATE
}

object BleServicesDatabase {
    private val standardServices = mapOf(
        "1800" to BleServiceInfo(
            uuid = "1800",
            name = "Generic Access",
            description = "Основной сервис для настройки соединения",
            icon = Icons.Default.Settings,
            characteristics = listOf(
                BleCharacteristicInfo(
                    uuid = "2A00",
                    name = "Device Name",
                    description = "Имя устройства",
                    properties = setOf(CharacteristicProperty.READ)
                ),
                BleCharacteristicInfo(
                    uuid = "2A01",
                    name = "Appearance",
                    description = "Внешний вид устройства",
                    properties = setOf(CharacteristicProperty.READ)
                )
            )
        ),
        "1801" to BleServiceInfo(
            uuid = "1801",
            name = "Generic Attribute",
            description = "Сервис атрибутов GATT",
            icon = Icons.Default.Info
        ),
        "180A" to BleServiceInfo(
            uuid = "180A",
            name = "Device Information",
            description = "Информация об устройстве",
            icon = Icons.Default.DeviceHub,
            characteristics = listOf(
                BleCharacteristicInfo(
                    uuid = "2A29",
                    name = "Manufacturer Name",
                    description = "Производитель",
                    properties = setOf(CharacteristicProperty.READ)
                ),
                BleCharacteristicInfo(
                    uuid = "2A24",
                    name = "Model Number",
                    description = "Номер модели",
                    properties = setOf(CharacteristicProperty.READ)
                ),
                BleCharacteristicInfo(
                    uuid = "2A25",
                    name = "Serial Number",
                    description = "Серийный номер",
                    properties = setOf(CharacteristicProperty.READ)
                ),
                BleCharacteristicInfo(
                    uuid = "2A27",
                    name = "Hardware Revision",
                    description = "Версия железа",
                    properties = setOf(CharacteristicProperty.READ)
                ),
                BleCharacteristicInfo(
                    uuid = "2A26",
                    name = "Firmware Revision",
                    description = "Версия прошивки",
                    properties = setOf(CharacteristicProperty.READ)
                )
            )
        ),
        "180F" to BleServiceInfo(
            uuid = "180F",
            name = "Battery Service",
            description = "Информация о батарее",
            icon = Icons.Default.BatteryFull,
            characteristics = listOf(
                BleCharacteristicInfo(
                    uuid = "2A19",
                    name = "Battery Level",
                    description = "Уровень заряда",
                    properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
                )
            )
        ),
        "FFE0" to BleServiceInfo(
            uuid = "FFE0",
            name = "LED Control Service",
            description = "Управление LED устройством",
            icon = Icons.Default.LightbulbCircle,
            characteristics = listOf(
                BleCharacteristicInfo(
                    uuid = "FFE1",
                    name = "LED Control",
                    description = "Управление LED",
                    properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE)
                )
            )
        )
    )
    
    fun getServiceInfo(uuid: String): BleServiceInfo {
        // Извлекаем короткий UUID (последние 4 символа)
        val shortUuid = uuid.takeLast(4).uppercase()
        println("### $shortUuid")
        return standardServices[shortUuid] ?: BleServiceInfo(
            uuid = uuid,
            name = "Unknown Service",
            description = "Неизвестный сервис",
            icon = Icons.Default.QuestionMark
        )
    }
    
    fun getCharacteristicInfo(uuid: String): BleCharacteristicInfo {
        // TODO: Добавить базу характеристик
        return BleCharacteristicInfo(
            uuid = uuid,
            name = "Unknown Characteristic",
            description = "Неизвестная характеристика",
            properties = emptySet()
        )
    }
} 