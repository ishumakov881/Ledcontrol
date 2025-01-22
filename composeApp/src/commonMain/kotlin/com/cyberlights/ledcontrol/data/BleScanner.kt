package com.cyberlights.ledcontrol.data

import com.cyberlights.ledcontrol.data.models.BleDevice
import com.cyberlights.ledcontrol.data.models.BleLogEntry
import kotlinx.coroutines.flow.StateFlow

expect interface BleScanner {
    val isScanning: StateFlow<Boolean>
    val devices: StateFlow<List<BleDevice>>
    val logs: StateFlow<List<BleLogEntry>>
    
    fun hasRequiredPermissions(): Boolean
    fun startScan()
    fun stopScan()
    fun clearDevices()
    fun clearLogs()
    
    // Connection methods
    fun connect(device: BleDevice)
    fun disconnect(device: BleDevice)
} 