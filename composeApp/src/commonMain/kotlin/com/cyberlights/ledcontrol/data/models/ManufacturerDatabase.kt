package com.cyberlights.ledcontrol.data.models

expect class ManufacturerDatabase {
    fun getManufacturerName(code: Int): String
    
    companion object {
        fun create(): ManufacturerDatabase
    }
} 