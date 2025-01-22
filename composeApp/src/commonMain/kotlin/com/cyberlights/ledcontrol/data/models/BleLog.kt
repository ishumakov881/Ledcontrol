package com.cyberlights.ledcontrol.data.models

data class BleLogEntry(
    val timestamp: Long = System.currentTimeMillis(),
    val type: LogType,
    val data: ByteArray,
    val description: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BleLogEntry

        if (timestamp != other.timestamp) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}

enum class LogType(val prefix: String) {
    SEND("→ "),
    RECEIVE("← ")
}

fun ByteArray.toHexString(): String = joinToString("") { 
    "%02X".format(it) 
} 