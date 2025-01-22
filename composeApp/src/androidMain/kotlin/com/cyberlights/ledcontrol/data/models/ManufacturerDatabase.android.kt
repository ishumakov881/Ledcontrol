package com.cyberlights.ledcontrol.data.models

import android.content.Context
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
            
            // TODO: Загрузить из assets/manufacturers.csv
            // Пока используем тестовые данные
            manufacturers[0x004C] = "Apple Inc."
            manufacturers[0x0075] = "Samsung Electronics Co. Ltd."
            manufacturers[0x038F] = "Xiaomi Inc."
            
            return ManufacturerDatabase(manufacturers)
        }
    }
} 