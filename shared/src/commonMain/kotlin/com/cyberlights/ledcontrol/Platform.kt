package com.cyberlights.ledcontrol

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform