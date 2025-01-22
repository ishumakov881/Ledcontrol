package com.cyberlights.ledcontrol.data.models

import android.content.Context
import com.cyberlights.ledcontrol.AndroidApp
import java.io.BufferedReader
import java.io.InputStreamReader

actual class ManufacturerDatabase private constructor(

    private val manufacturers: Map<Int, String>
) {
    actual fun getManufacturerName(code: Int): String {
        return manufacturers[code] ?: "Unknown (0x${String.format("%04X", code)})"
    }
    
    actual companion object {
        private var instance: ManufacturerDatabase? = null
        
        actual fun create(): ManufacturerDatabase {
            return instance ?: synchronized(this) {
                instance ?: createDatabase().also { instance = it }
            }
        }
        
        private fun createDatabase(): ManufacturerDatabase {
            val manufacturers = mutableMapOf<Int, String>()
            
            try {
                AndroidApp.applicationContext.assets.open("manufacturers.csv").bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        val parts = line.split(",", limit = 2)
                        if (parts.size == 2) {
                            val code = parts[0].trim().removePrefix("0x").toInt(16)
                            val name = parts[1].trim().removeSurrounding("\"")
                            manufacturers[code] = name
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error loading manufacturers: ${e.message}")
            }
            
            return ManufacturerDatabase(manufacturers)
        }
    }
} 