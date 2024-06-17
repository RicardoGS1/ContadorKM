package com.virtualworld.contadorkm.navegation

import androidx.navigation.NavController
import androidx.navigation.navDeepLink

sealed class DestinationApp(val route: String)
{

    object Home : DestinationApp(route = "home")



    object CurrentRun : DestinationApp("current_run")
    {

        val currentRunUriPattern = "https://runtrack.sdevprem.com/$route"
        val deepLinks = listOf(navDeepLink {
            uriPattern = currentRunUriPattern
        })
    }


    object Profile : DestinationApp(route = "profile")


    //global navigation
    companion object
    {
        fun navigateToCurrentRunScreen(navController: NavController)
        {
            navController.navigate(CurrentRun.route)
        }
    }



}
