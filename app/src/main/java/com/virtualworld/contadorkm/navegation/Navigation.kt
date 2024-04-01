package com.virtualworld.contadorkm.navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues = PaddingValues())
{
    SetupNavGraph(navController = navController, paddingValues = paddingValues)
}

@Composable
private fun SetupNavGraph(navController: NavHostController, paddingValues: PaddingValues = PaddingValues())
{
    NavHost(navController = navController, startDestination = BottomNavDestination.Home.route) {
           homeNavigation(navController, paddingValues)

        composable(route = BottomNavDestination.Profile.route) {
            // ProfileScreen(paddingValues.calculateBottomPadding())
        }

        composable(route = Destination.CurrentRun.route, deepLinks = Destination.CurrentRun.deepLinks) {
           //   CurrentRunScreen(navController)
        }

        composable(route = Destination.OnBoardingDestination.route) {
            // OnBoardScreen(navController = navController)
        }
    }

}