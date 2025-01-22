package com.cyberlights.ledcontrol.data.models

class ProtocolAnalyzer {
    companion object {
        // Известные паттерны команд
        private val PATTERNS = mapOf(
            "RGB" to Regex("^(0x)?([0-9A-F]{6})\$", RegexOption.IGNORE_CASE),
            "ON/OFF" to Regex("^(0x)?(00|01|FF)\$", RegexOption.IGNORE_CASE),
            "BRIGHTNESS" to Regex("^(0x)?([0-9A-F]{2})\$", RegexOption.IGNORE_CASE)
        )
        
        fun analyze(data: ByteArray): String {
            val hex = data.toHexString()
            
            // Поиск известных паттернов
            PATTERNS.forEach { (type, pattern) ->
                if (pattern.matches(hex)) {
                    return when(type) {
                        "RGB" -> "RGB Color Command: #${hex.takeLast(6)}"
                        "ON/OFF" -> when(hex.takeLast(2)) {
                            "00" -> "Power Command: OFF"
                            "01", "FF" -> "Power Command: ON"
                            else -> "Unknown Power Command"
                        }
                        "BRIGHTNESS" -> "Brightness Command: ${
                            (hex.takeLast(2).toIntOrNull(16) ?: 0) * 100 / 255
                        }%"
                        else -> "Unknown Command"
                    }
                }
            }
            
            // Анализ структуры данных
            return when {
                hex.length == 2 -> "Single Byte Command"
                hex.length == 4 -> "Two Byte Command"
                hex.length == 6 -> "Three Byte Command (Possible RGB)"
                hex.length > 6 -> "Complex Command (${hex.length/2} bytes)"
                else -> "Unknown Format"
            }
        }
    }
} 