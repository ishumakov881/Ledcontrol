package com.cyberlights.ledcontrol

import android.bluetooth.BluetoothManager
import android.content.Context
import com.cyberlights.ledcontrol.data.AndroidBleScanner
import com.cyberlights.ledcontrol.data.BleScanner



actual fun getPlatformBleScanner(): BleScanner {
    val bluetoothManager = AndroidApp.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    return AndroidBleScanner(AndroidApp.applicationContext, bluetoothManager)
} 