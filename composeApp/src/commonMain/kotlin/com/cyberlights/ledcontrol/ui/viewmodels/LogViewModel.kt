package com.cyberlights.ledcontrol.ui.viewmodels

import com.cyberlights.ledcontrol.data.models.BleLogEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LogViewModel {
    private val _logs = MutableStateFlow<List<BleLogEntry>>(emptyList())
    val logs: StateFlow<List<BleLogEntry>> = _logs.asStateFlow()
    
    fun addLog(entry: BleLogEntry) {
        _logs.value = _logs.value + entry
    }
    
    fun clearLogs() {
        _logs.value = emptyList()
    }
    
    @OptIn(ExperimentalStdlibApi::class)
    fun exportLogs(): String {
        return buildString {
            _logs.value.forEach { entry ->
                appendLine("${entry.timestamp} ${entry.type.prefix}${entry.data.toHexString()}")
                if (entry.description.isNotEmpty()) {
                    appendLine("Description: ${entry.description}")
                }
                appendLine()
            }
        }
    }
} 