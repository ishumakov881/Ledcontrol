package com.cyberlights.ledcontrol.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.bluetooth.BluetoothDevice.TRANSPORT_AUTO
import android.bluetooth.BluetoothDevice.TRANSPORT_LE
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanRecord
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.cyberlights.ledcontrol.MainActivity
import com.cyberlights.ledcontrol.data.models.BleDevice
import com.cyberlights.ledcontrol.data.models.BleLogEntry
import com.cyberlights.ledcontrol.data.models.ConnectionState
import com.cyberlights.ledcontrol.data.models.LogType
import com.cyberlights.ledcontrol.data.models.ManufacturerDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class AndroidBleScanner(
    private val context: Context,
    private val bluetoothManager: BluetoothManager
) : BleScanner {
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val bleScanner = bluetoothAdapter?.bluetoothLeScanner
    
    private val _isScanning = MutableStateFlow(false)
    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()
    
    private val _devices = MutableStateFlow<List<BleDevice>>(emptyList())
    override val devices: StateFlow<List<BleDevice>> = _devices.asStateFlow()

    private val _logs = MutableStateFlow<List<BleLogEntry>>(emptyList())
    override val logs: StateFlow<List<BleLogEntry>> = _logs.asStateFlow()

    private var gatt: BluetoothGatt? = null

    private val manufacturerDb = ManufacturerDatabase.create()

    private fun addLog(type: LogType, data: ByteArray, description: String = "") {


        println("$type :: ${data.contentToString()} :: $description")

        val entry = BleLogEntry(
            type = type,
            data = data,
            description = description
        )
        _logs.value = _logs.value + entry
    }

    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            println("GATT status: $status, newState: $newState")
            
            // Логируем статус операции
            val statusStr = when (status) {
                BluetoothGatt.GATT_SUCCESS -> "GATT_SUCCESS"
                BluetoothGatt.GATT_FAILURE -> "GATT_FAILURE"
                BluetoothGatt.GATT_CONNECTION_CONGESTED -> "GATT_CONNECTION_CONGESTED"
                BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION -> "GATT_INSUFFICIENT_AUTHENTICATION"
                BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION -> "GATT_INSUFFICIENT_ENCRYPTION"
                BluetoothGatt.GATT_INVALID_OFFSET -> "GATT_INVALID_OFFSET"
                BluetoothGatt.GATT_READ_NOT_PERMITTED -> "GATT_READ_NOT_PERMITTED"
                BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED -> "GATT_REQUEST_NOT_SUPPORTED"
                BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> "GATT_WRITE_NOT_PERMITTED"
                133 -> "GATT_HCI_ERROR_HARDWARE_FAILURE" // Ошибка на уровне HCI - проблема с железом
                else -> "Unknown status: $status"
            }
            println("GATT operation status: $statusStr")

            // Добавляем специальную обработку для кода 133
            if (status == 133) {
                // Логируем ошибку
                addLog(
                    type = LogType.RECEIVE,
                    data = byteArrayOf(status.toByte()),
                    description = "Hardware failure detected, disconnecting"
                )
                
                // Закрываем соединение
                gatt.close()
                //gatt = null
                
                // Обновляем состояние устройства
                updateDeviceState(gatt.device.address, ConnectionState.DISCONNECTED)
            }

            // Логируем новое состояние
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    println("STATE_CONNECTED")
                    updateDeviceState(gatt.device.address, ConnectionState.CONNECTED)
                    addLog(
                        type = LogType.RECEIVE,
                        data = byteArrayOf(1),
                        description = "Connected to ${gatt.device.address}"
                    )
                    gatt.discoverServices()
                }
                BluetoothProfile.STATE_CONNECTING -> {
                    println("STATE_CONNECTING")
                    updateDeviceState(gatt.device.address, ConnectionState.CONNECTING)
                    addLog(
                        type = LogType.RECEIVE,
                        data = byteArrayOf(2),
                        description = "Connecting to ${gatt.device.address}"
                    )
                }
                BluetoothProfile.STATE_DISCONNECTING -> {
                    println("STATE_DISCONNECTING")
                    addLog(
                        type = LogType.RECEIVE,
                        data = byteArrayOf(3),
                        description = "Disconnecting from ${gatt.device.address}"
                    )
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    println("STATE_DISCONNECTED")
                    updateDeviceState(gatt.device.address, ConnectionState.DISCONNECTED)
                    addLog(
                        type = LogType.RECEIVE,
                        data = byteArrayOf(0),
                        description = "Disconnected from ${gatt.device.address}"
                    )
                    gatt.close()
                }
                else -> {
                    println("Unknown state: $newState")
                    addLog(
                        type = LogType.RECEIVE,
                        data = byteArrayOf(4),
                        description = "Unknown state ($newState) for ${gatt.device.address}"
                    )
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val services = gatt.services.map { it.uuid.toString() }
                updateDeviceServices(gatt.device.address, services)
                addLog(
                    type = LogType.RECEIVE,
                    data = byteArrayOf(2),
                    description = "Services discovered: ${services.joinToString()}"
                )
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                addLog(
                    type = LogType.RECEIVE,
                    data = value,
                    description = "Read from ${characteristic.uuid}: ${value.joinToString(", ") { "%02X".format(it) }}"
                )
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                addLog(
                    type = LogType.SEND,
                    data = characteristic.value ?: byteArrayOf(),
                    description = "Write to ${characteristic.uuid}: ${characteristic.value?.joinToString(", ") { "%02X".format(it) }}"
                )
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            addLog(
                type = LogType.RECEIVE,
                data = value,
                description = "Notification from ${characteristic.uuid}: ${value.joinToString(", ") { "%02X".format(it) }}"
            )
        }
    }

    private fun updateDeviceState(address: String, state: ConnectionState) {
        println("Updating device state: $address -> $state")
        val currentList = _devices.value.toMutableList()
        val index = currentList.indexOfFirst { it.address == address }
        if (index >= 0) {
            currentList[index] = currentList[index].copy(connectionState = state)
            _devices.value = currentList
            println("Device state updated: ${currentList[index]}")
        } else {
            println("Device not found in list: $address")
        }
    }

    private fun updateDeviceServices(address: String, services: List<String>) {
        val currentList = _devices.value.toMutableList()
        val index = currentList.indexOfFirst { it.address == address }
        if (index >= 0) {
            currentList[index] = currentList[index].copy(services = services)
            _devices.value = currentList
        }
    }

    private val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
    
    private fun parseManufacturerData(scanRecord: ScanRecord?): Pair<String?, String?> {
        val manufacturerData = scanRecord?.manufacturerSpecificData ?: return null to null
        if (manufacturerData.size() == 0) return null to null
        
        val key = manufacturerData.keyAt(0)
        val data = manufacturerData.get(key)
        
        val name = manufacturerDb.getManufacturerName(key)
        val hexData = data?.joinToString(" ") { String.format("%02X", it) }
        
        return name to hexData
    }
    
    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val (manufacturerName, manufacturerData) = parseManufacturerData(result.scanRecord)
            
            val bleDevice = BleDevice(
                name = device.name ?: "Unknown",
                address = device.address,
                rssi = result.rssi,
                manufacturerName = manufacturerName,
                manufacturerData = manufacturerData,
                services = device.uuids?.map { it.toString() } ?: emptyList(),
                isBonded = device.bondState == BluetoothDevice.BOND_BONDED
            )
            
            val currentList = _devices.value.toMutableList()
            val existingIndex = currentList.indexOfFirst { it.address == bleDevice.address }
            
            if (existingIndex >= 0) {
                currentList[existingIndex] = bleDevice
            } else {
                currentList.add(bleDevice)
            }
            
            _devices.value = currentList
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            _isScanning.value = false
        }
    }
    
    override fun hasRequiredPermissions(): Boolean {
        return requiredPermissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    @SuppressLint("MissingPermission")
    override fun startScan() {
        if (!hasRequiredPermissions()) {
            (context as? MainActivity)?.requestBlePermissions()
            return
        }

        if (bluetoothAdapter?.isEnabled == false) {
            (context as? MainActivity)?.requestEnableBluetooth()
            return
        }
        
        bleScanner?.startScan(scanCallback)
        _isScanning.value = true
    }
    
    @SuppressLint("MissingPermission")
    override fun stopScan() {
        if (!hasRequiredPermissions()) return
        
        bleScanner?.stopScan(scanCallback)
        _isScanning.value = false
    }
    
    override fun clearDevices() {
        _devices.value = emptyList()
    }

    override fun clearLogs() {
        _logs.value = emptyList()
    }

    @SuppressLint("MissingPermission")
    override fun connect(device: BleDevice) {
        if (!hasRequiredPermissions()) {
            (context as? MainActivity)?.requestBlePermissions()
            return
        }

        // Останавливаем сканирование перед подключением
        if (_isScanning.value) {
            stopScan()
        }

        // Отключаемся от предыдущего устройства если было подключено
        disconnect(device)

        // Получаем устройство по адресу
        val bluetoothDevice = bluetoothAdapter?.getRemoteDevice(device.address)
        if (bluetoothDevice == null) {
            updateDeviceState(device.address, ConnectionState.DISCONNECTED)
            return
        }

        // Обновляем состояние на "подключение"
        updateDeviceState(device.address, ConnectionState.CONNECTING)

        // Подключаемся
        gatt = bluetoothDevice.connectGatt(context, true, gattCallback)
        //gatt = bluetoothDevice.connectGatt(context, false, gattCallback, TRANSPORT_LE/*TRANSPORT_AUTO*/)
    }

    @SuppressLint("MissingPermission")
    override fun disconnect(device: BleDevice) {
        gatt?.disconnect()
        gatt?.close()
        gatt = null
        updateDeviceState(device.address, ConnectionState.DISCONNECTED)
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 123
    }

} 