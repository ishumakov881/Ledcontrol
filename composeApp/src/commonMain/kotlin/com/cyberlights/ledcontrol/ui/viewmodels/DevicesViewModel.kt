package com.cyberlights.ledcontrol.ui.viewmodels

import com.cyberlights.ledcontrol.data.BleScanner
import com.cyberlights.ledcontrol.data.models.BleDevice
import kotlinx.coroutines.flow.StateFlow

class DevicesViewModel(
    private val bleScanner: BleScanner
) {
    val isScanning: StateFlow<Boolean> = bleScanner.isScanning
    val devices: StateFlow<List<BleDevice>> = bleScanner.devices
    
    fun startScan() {
        bleScanner.startScan()
    }
    
    fun stopScan() {
        bleScanner.stopScan()
    }
    
    fun clearDevices() {
        bleScanner.clearDevices()
    }
    
    fun connectToDevice(device: BleDevice) {
        bleScanner.connect(device)
    }
    
    fun disconnectFromDevice(device: BleDevice) {
        bleScanner.disconnect(device)
    }
} 