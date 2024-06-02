package com.virtualworld.contadorkm.navegation

import androidx.navigation.NavController
import androidx.navigation.navDeepLink

sealed class DestinationApp(val route: String)
{


    object OnBoardingDestination : DestinationApp("on_boarding")
    {
        fun navigateToHome(navController: NavController)
        {
            navController.navigate(DestinationApp.Home.route) {
                popUpTo(route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }







    object Home : DestinationApp(route = "home")
    {

        fun navigateToOnBoardingScreen(navController: NavController)
        {
            navController.navigate(DestinationApp.OnBoardingDestination.route)
        }

        object RecentRun : DestinationApp("recent_run")
        {
            fun navigateToRunningHistoryScreen(navController: NavController)
            {
                navController.navigate(RunningHistory.route)
            }
        }

        object RunningHistory : DestinationApp("running_history")

    }







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
