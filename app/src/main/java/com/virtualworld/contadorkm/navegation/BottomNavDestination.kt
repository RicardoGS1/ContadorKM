package com.virtualworld.contadorkm.navegation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.virtualworld.contadorkm.R


sealed class BottomNavDestination(route: String, @DrawableRes val icon: Int) : Destination(route)
{

    @Composable
    fun getIconVector() = ImageVector.vectorResource(icon)

    object Home : BottomNavDestination(route = "home", icon = R.drawable.ic_menu)
    {

        fun navigateToOnBoardingScreen(navController: NavController)
        {
            navController.navigate(Destination.OnBoardingDestination.route)
        }

        object RecentRun : Destination("recent_run")
        {
            fun navigateToRunningHistoryScreen(navController: NavController)
            {
                navController.navigate(RunningHistory.route)
            }
        }

        object RunningHistory : Destination("running_history")

    }

    object Profile : BottomNavDestination(route = "profile", icon = R.drawable.ic_profile)

}