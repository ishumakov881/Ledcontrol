package com.cyberlights.ledcontrol.data.models

data class BleDevice(
    val name: String,
    val address: String,
    val rssi: Int,
    val manufacturerName: String? = null,
    val manufacturerData: String? = null,
    val services: List<String> = emptyList(),
    val isBonded: Boolean = false,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED
) {
    val displayName: String
        get() = name.takeIf { it.isNotBlank() } ?: "Unknown Device"
        
    companion object {
        const val RSSI_MAX = -50 // Excellent signal
        const val RSSI_GOOD = -70 // Good signal
        const val RSSI_WEAK = -80 // Weak signal
    }
}

enum class ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED
} 