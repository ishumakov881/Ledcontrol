package com.cyberlights.ledcontrol

import com.cyberlights.ledcontrol.data.BleScanner
import com.cyberlights.ledcontrol.data.IosBleScanner

actual fun getPlatformBleScanner(): BleScanner = IosBleScanner() 