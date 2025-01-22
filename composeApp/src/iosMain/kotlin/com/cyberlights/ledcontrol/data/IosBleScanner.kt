package com.cyberlights.ledcontrol.data

import com.cyberlights.ledcontrol.data.models.BleDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IosBleScanner : BleScanner {
    private val _isScanning = MutableStateFlow(false)
    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()
    
    private val _devices = MutableStateFlow<List<BleDevice>>(emptyList())
    override val devices: StateFlow<List<BleDevice>> = _devices.asStateFlow()
    
    override fun hasRequiredPermissions(): Boolean {
        // TODO: Implement iOS permission check
        return true
    }
    
    override fun startScan() {
        // TODO: Implement iOS scanning
        _isScanning.value = true
    }
    
    override fun stopScan() {
        // TODO: Implement iOS scanning stop
        _isScanning.value = false
    }
    
    override fun clearDevices() {
        _devices.value = emptyList()
    }

    override fun connect(device: BleDevice) {
        // TODO: Implement iOS connection
    }

    override fun disconnect(device: BleDevice) {
        // TODO: Implement iOS disconnection
    }
} 