package com.virtualworld.contadorkm.ui.screen.main


import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.virtualworld.contadorkm.navegation.AppBottomBar
import com.virtualworld.contadorkm.navegation.AppBottomFAB

import com.virtualworld.contadorkm.navegation.DestinationApp
import com.virtualworld.contadorkm.navegation.Navigation

import kotlinx.coroutines.delay


@Composable
fun MainScreen(navHostController: NavHostController, viewModel: MainScreenViewModel = hiltViewModel())
{
    //observar la navegación
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    var shouldShowBottomNav by rememberSaveable { mutableStateOf(false) }//mostrar bottomBar para anmasoion
    var shouldShowFAB by rememberSaveable { mutableStateOf(false) }      //mostrar FAB para animacion
    var hideBottomItems by rememberSaveable { mutableStateOf(true) }     //la barra completa



    //modifica (hideBottomItems) para ocultar los botones si esta la screen is ruun o boarding
    hideBottomItems = when (navBackStackEntry?.destination?.route)
    {
        DestinationApp.CurrentRun.route -> true
        DestinationApp.OnBoardingDestination.route -> true
        else -> false
    }

    //modifica (shouldShowFAB) (shouldShowBottomNav) para later hide los botonesBar and retrasa el efecto de bottom flotant
    //se usa el launchefect para que soviver al chancger screen
    LaunchedEffect(hideBottomItems) {
        if (hideBottomItems)
        {
            shouldShowFAB = false
            delay(150)
            shouldShowBottomNav = false


        } else
        {
            shouldShowBottomNav = true
            delay(150)
            shouldShowFAB = true
        }
    }



    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navHostController,visible = shouldShowBottomNav)
        },

        floatingActionButton = {
            AppBottomFAB(navHostController = navHostController, visible = shouldShowFAB)
        },

        floatingActionButtonPosition = FabPosition.Center,

    ) {

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Navigation(navHostController, it)
        }
    }

}





