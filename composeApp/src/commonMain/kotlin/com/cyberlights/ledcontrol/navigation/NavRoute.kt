package com.cyberlights.ledcontrol.navigation

sealed class NavRoute {
    data object Devices : NavRoute() {
        data object ScanList : NavRoute()
    }
    
    data object Controls : NavRoute()
    data object Effects : NavRoute()
    data object Settings : NavRoute()
} 