package com.virtualworld.contadorkm.navegation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.virtualworld.contadorkm.R







sealed class BottomBarNavDestination(val route: String, @DrawableRes val icon: Int)
{

    @Composable
    fun getIconVector() = ImageVector.vectorResource(icon)
    object Home : BottomBarNavDestination(
        route = DestinationApp.Home.route,
        icon =  R.drawable.ic_menu
    )

    object Profile : BottomBarNavDestination(
        route = DestinationApp.Profile.route,
        icon =  R.drawable.ic_profile
    )

    object CurrentRun : BottomBarNavDestination(
        route = DestinationApp.CurrentRun.route,
        icon = R.drawable.ic_run
    )




}