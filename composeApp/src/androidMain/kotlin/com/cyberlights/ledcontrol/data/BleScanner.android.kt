package com.cyberlights.ledcontrol.data

import android.content.Context
import com.cyberlights.ledcontrol.data.models.BleDevice
import com.cyberlights.ledcontrol.data.models.BleLogEntry
import kotlinx.coroutines.flow.StateFlow

actual interface BleScanner {
    actual val isScanning: StateFlow<Boolean>
    actual val devices: StateFlow<List<BleDevice>>
    actual val logs: StateFlow<List<BleLogEntry>>
    
    actual fun hasRequiredPermissions(): Boolean
    actual fun startScan()
    actual fun stopScan()
    actual fun clearDevices()
    
    actual fun connect(device: BleDevice)
    actual fun disconnect(device: BleDevice)
} 