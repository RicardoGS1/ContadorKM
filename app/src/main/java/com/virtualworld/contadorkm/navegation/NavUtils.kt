package com.virtualworld.contadorkm.navegation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.virtualworld.contadorkm.navegation.BottomNavDestination


fun NavController.navigateToBottomNavDestination(item: BottomNavDestination) {
    navigate(item.route) {
        popUpTo(graph.findStartDestination().id) {
            this.saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}