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
    WRITE_WITHOUT_RESPONSE,
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
        "1802" to BleServiceInfo(
            uuid = "1802",
            name = "Immediate Alert",
            description = "Мгновенное оповещение",
            icon = Icons.Default.Notifications
        ),
        "1803" to BleServiceInfo(
            uuid = "1803",
            name = "Link Loss",
            description = "Потеря соединения",
            icon = Icons.Default.LinkOff
        ),
        "1804" to BleServiceInfo(
            uuid = "1804",
            name = "Tx Power",
            description = "Мощность передачи",
            icon = Icons.Default.SignalCellular4Bar
        ),
        "1805" to BleServiceInfo(
            uuid = "1805",
            name = "Current Time",
            description = "Текущее время",
            icon = Icons.Default.Timer
        ),
        "1806" to BleServiceInfo(
            uuid = "1806",
            name = "Reference Time Update",
            description = "Обновление эталонного времени",
            icon = Icons.Default.Update
        ),
        "1807" to BleServiceInfo(
            uuid = "1807",
            name = "Next DST Change",
            description = "Следующее изменение летнего времени",
            icon = Icons.Default.Schedule
        ),
        "1808" to BleServiceInfo(
            uuid = "1808",
            name = "Glucose",
            description = "Измерение глюкозы",
            icon = Icons.Default.Bloodtype
        ),
        "1809" to BleServiceInfo(
            uuid = "1809",
            name = "Health Thermometer",
            description = "Медицинский термометр",
            icon = Icons.Default.Thermostat
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
        "180D" to BleServiceInfo(
            uuid = "180D",
            name = "Heart Rate",
            description = "Измерение пульса",
            icon = Icons.Default.Favorite
        ),
        "180E" to BleServiceInfo(
            uuid = "180E",
            name = "Phone Alert Status",
            description = "Статус оповещений телефона",
            icon = Icons.Default.PhoneAndroid
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
        "1810" to BleServiceInfo(
            uuid = "1810",
            name = "Blood Pressure",
            description = "Измерение давления",
            icon = Icons.Default.HealthAndSafety
        ),
        "1811" to BleServiceInfo(
            uuid = "1811",
            name = "Alert Notification",
            description = "Служба уведомлений",
            icon = Icons.Default.NotificationsActive
        ),
        "1812" to BleServiceInfo(
            uuid = "1812",
            name = "Human Interface Device",
            description = "Устройство ввода",
            icon = Icons.Default.Keyboard
        ),
        "1813" to BleServiceInfo(
            uuid = "1813",
            name = "Scan Parameters",
            description = "Параметры сканирования",
            icon = Icons.Default.Scanner
        ),
        "1814" to BleServiceInfo(
            uuid = "1814",
            name = "Running Speed and Cadence",
            description = "Скорость бега и каденс",
            icon = Icons.Default.DirectionsRun
        ),
        "1815" to BleServiceInfo(
            uuid = "1815",
            name = "Automation IO",
            description = "Автоматизация ввода/вывода",
            icon = Icons.Default.Input
        ),
        "1816" to BleServiceInfo(
            uuid = "1816",
            name = "Cycling Speed and Cadence",
            description = "Скорость велосипеда и каденс",
            icon = Icons.Default.PedalBike
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
        ),
        "FFF0" to BleServiceInfo(
            uuid = "FFF0",
            name = "ISSC Transparent Service",
            description = "Сервис прозрачной передачи данных ISSC",
            icon = Icons.Default.DataObject,
            characteristics = listOf(
                BleCharacteristicInfo(
                    uuid = "FFF1",
                    name = "ISSC Transparent TX",
                    description = "Передача данных (TX)",
                    properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE)
                ),
                BleCharacteristicInfo(
                    uuid = "FFF2",
                    name = "ISSC Transparent RX",
                    description = "Прием данных (RX)",
                    properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
                )
            )
        )
    )
    
    private val standardCharacteristics = mapOf(
        "2A00" to BleCharacteristicInfo(
            uuid = "2A00",
            name = "Device Name",
            description = "Имя устройства",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A01" to BleCharacteristicInfo(
            uuid = "2A01",
            name = "Appearance",
            description = "Внешний вид устройства",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A02" to BleCharacteristicInfo(
            uuid = "2A02",
            name = "Peripheral Privacy Flag",
            description = "Флаг приватности периферийного устройства",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A03" to BleCharacteristicInfo(
            uuid = "2A03",
            name = "Reconnection Address",
            description = "Адрес переподключения",
            properties = setOf(CharacteristicProperty.WRITE)
        ),
        "2A04" to BleCharacteristicInfo(
            uuid = "2A04",
            name = "Peripheral Preferred Connection Parameters",
            description = "Предпочтительные параметры соединения",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A05" to BleCharacteristicInfo(
            uuid = "2A05",
            name = "Service Changed",
            description = "Изменение сервиса",
            properties = setOf(CharacteristicProperty.INDICATE)
        ),
        "2A06" to BleCharacteristicInfo(
            uuid = "2A06",
            name = "Alert Level",
            description = "Уровень оповещения",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE)
        ),
        "2A07" to BleCharacteristicInfo(
            uuid = "2A07",
            name = "Tx Power Level",
            description = "Уровень мощности передачи",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A08" to BleCharacteristicInfo(
            uuid = "2A08",
            name = "Date Time",
            description = "Дата и время",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE)
        ),
        "2A09" to BleCharacteristicInfo(
            uuid = "2A09",
            name = "Day of Week",
            description = "День недели",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A19" to BleCharacteristicInfo(
            uuid = "2A19",
            name = "Battery Level",
            description = "Уровень заряда",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        ),
        "2A24" to BleCharacteristicInfo(
            uuid = "2A24",
            name = "Model Number",
            description = "Номер модели",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A25" to BleCharacteristicInfo(
            uuid = "2A25",
            name = "Serial Number",
            description = "Серийный номер",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A26" to BleCharacteristicInfo(
            uuid = "2A26",
            name = "Firmware Revision",
            description = "Версия прошивки",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A27" to BleCharacteristicInfo(
            uuid = "2A27",
            name = "Hardware Revision",
            description = "Версия железа",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A28" to BleCharacteristicInfo(
            uuid = "2A28",
            name = "Software Revision",
            description = "Версия ПО",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A29" to BleCharacteristicInfo(
            uuid = "2A29",
            name = "Manufacturer Name",
            description = "Производитель",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A2A" to BleCharacteristicInfo(
            uuid = "2A2A",
            name = "IEEE Regulatory Certification",
            description = "Сертификация IEEE",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A31" to BleCharacteristicInfo(
            uuid = "2A31",
            name = "Scan Refresh",
            description = "Обновление сканирования",
            properties = setOf(CharacteristicProperty.NOTIFY)
        ),
        "2A32" to BleCharacteristicInfo(
            uuid = "2A32",
            name = "Boot Keyboard Output Report",
            description = "Отчет вывода клавиатуры загрузки",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE, CharacteristicProperty.WRITE_WITHOUT_RESPONSE)
        ),
        "2A33" to BleCharacteristicInfo(
            uuid = "2A33",
            name = "Boot Mouse Input Report",
            description = "Отчет ввода мыши загрузки",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        ),
        "2A34" to BleCharacteristicInfo(
            uuid = "2A34",
            name = "Glucose Measurement Context",
            description = "Контекст измерения глюкозы",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        ),
        "2A35" to BleCharacteristicInfo(
            uuid = "2A35",
            name = "Blood Pressure Measurement",
            description = "Измерение кровяного давления",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        ),
        "2A36" to BleCharacteristicInfo(
            uuid = "2A36",
            name = "Intermediate Cuff Pressure",
            description = "Промежуточное давление в манжете",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        ),
        "2A37" to BleCharacteristicInfo(
            uuid = "2A37",
            name = "Heart Rate Measurement",
            description = "Измерение пульса",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        ),
        "2A38" to BleCharacteristicInfo(
            uuid = "2A38",
            name = "Body Sensor Location",
            description = "Расположение датчика на теле",
            properties = setOf(CharacteristicProperty.READ)
        ),
        "2A39" to BleCharacteristicInfo(
            uuid = "2A39",
            name = "Heart Rate Control Point",
            description = "Точка управления пульсом",
            properties = setOf(CharacteristicProperty.WRITE)
        ),
        "FFE1" to BleCharacteristicInfo(
            uuid = "FFE1",
            name = "LED Control",
            description = "Управление LED",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE)
        ),
        "FFF1" to BleCharacteristicInfo(
            uuid = "FFF1",
            name = "ISSC Transparent TX",
            description = "Передача данных (TX)",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.WRITE)
        ),
        "FFF2" to BleCharacteristicInfo(
            uuid = "FFF2",
            name = "ISSC Transparent RX",
            description = "Прием данных (RX)",
            properties = setOf(CharacteristicProperty.READ, CharacteristicProperty.NOTIFY)
        )
    )
    
    fun getServiceInfo(uuid: String): BleServiceInfo {
        if (uuid.length < 4) {
            return BleServiceInfo(
                uuid = uuid,
                name = "Invalid UUID",
                description = "Некорректный UUID (слишком короткий)",
                icon = Icons.Default.Error
            )
        }

        val shortUuid = when {
            uuid.contains("-") -> {
                // Для полного UUID формата XXXXXXXX-0000-1000-8000-00805f9b34fb
                // Проверяем правильный формат
                if (!uuid.matches(Regex("[0-9a-fA-F]{8}-0000-1000-8000-00805[fF]9[bB]34[fF][bB]"))) {
                    return BleServiceInfo(
                        uuid = uuid,
                        name = "Invalid UUID",
                        description = "Некорректный формат полного UUID",
                        icon = Icons.Default.Error
                    )
                }
                uuid.split("-")[0].takeLast(4).uppercase()
            }
            uuid.length == 4 -> {
                uuid.uppercase()
            }
            uuid.length == 8 -> {
                uuid.takeLast(4).uppercase()
            }
            else -> {
                return BleServiceInfo(
                    uuid = uuid,
                    name = "Invalid UUID",
                    description = "Некорректная длина UUID",
                    icon = Icons.Default.Error
                )
            }
        }
        
        // Сначала проверяем характеристики, так как SDK может вернуть их UUID в поле сервиса
        val characteristic = standardCharacteristics[shortUuid]
        if (characteristic != null) {
            return BleServiceInfo(
                uuid = uuid,
                name = characteristic.name,
                description = characteristic.description,
                icon = Icons.Default.Settings,
                characteristics = listOf(characteristic)
            )
        }
        
        // Если это не характеристика, ищем среди сервисов
        return standardServices[shortUuid] ?: BleServiceInfo(
            uuid = uuid,
            name = "Unknown Service",
            description = "Неизвестный сервис",
            icon = Icons.Default.QuestionMark
        )
    }
    
    fun getCharacteristicInfo(uuid: String): BleCharacteristicInfo {
        if (uuid.length < 4) {
            return BleCharacteristicInfo(
                uuid = uuid,
                name = "Invalid UUID",
                description = "Некорректный UUID (слишком короткий)",
                properties = emptySet()
            )
        }

        val shortUuid = when {
            uuid.contains("-") -> {
                // Для полного UUID формата XXXXXXXX-0000-1000-8000-00805f9b34fb
                // Проверяем правильный формат
                if (!uuid.matches(Regex("[0-9a-fA-F]{8}-0000-1000-8000-00805[fF]9[bB]34[fF][bB]"))) {
                    return BleCharacteristicInfo(
                        uuid = uuid,
                        name = "Invalid UUID",
                        description = "Некорректный формат полного UUID",
                        properties = emptySet()
                    )
                }
                uuid.split("-")[0].takeLast(4).uppercase()
            }
            uuid.length == 4 -> {
                // Короткий формат
                uuid.uppercase()
            }
            uuid.length == 8 -> {
                // Формат без дефисов, берем последние 4 символа
                uuid.takeLast(4).uppercase()
            }
            else -> {
                return BleCharacteristicInfo(
                    uuid = uuid,
                    name = "Invalid UUID",
                    description = "Некорректная длина UUID",
                    properties = emptySet()
                )
            }
        }
        
        return standardCharacteristics[shortUuid] ?: BleCharacteristicInfo(
            uuid = uuid,
            name = "Unknown Characteristic",
            description = "Неизвестная характеристика",
            properties = emptySet()
        )
    }
} 