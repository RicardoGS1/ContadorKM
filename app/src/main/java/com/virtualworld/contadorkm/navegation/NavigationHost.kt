package com.virtualworld.contadorkm.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.virtualworld.contadorkm.ui.screen.HomeScreen
import com.virtualworld.contadorkm.ui.screen.currentrun.CurrentRunScreen


@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues = PaddingValues())
{
    SetupNavGraph(navController = navController, paddingValues = paddingValues)
}

@Composable
private fun SetupNavGraph(navController: NavHostController, paddingValues: PaddingValues = PaddingValues())
{
    NavHost(navController = navController, startDestination = DestinationApp.Home.RecentRun.route) {

        composable(route = DestinationApp.Home.RecentRun.route) {
            HomeScreen(navController = navController, bottomPadding = paddingValues.calculateBottomPadding())
        }

        composable(route = DestinationApp.Home.RunningHistory.route) {
            // RunningHistoryScreen(
            //     paddingValues = paddingValues,
            //      navController = navController
            //  )
        }

        composable(route = DestinationApp.Profile.route) {
            // ProfileScreen(paddingValues.calculateBottomPadding())
        }




        composable(route = DestinationApp.CurrentRun.route, deepLinks = DestinationApp.CurrentRun.deepLinks) {
              CurrentRunScreen(navController)
        }

        composable(route = DestinationApp.OnBoardingDestination.route) {
            // OnBoardScreen(navController = navController)
        }
    }

}