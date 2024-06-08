package com.virtualworld.contadorkm.ui.screen.currentrun

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.virtualworld.contadorkm.core.location.LocationUtils
import com.virtualworld.contadorkm.ui.screen.currentrun.component.CurrentRunBottomCard
import com.virtualworld.contadorkm.ui.screen.currentrun.component.CurrentRunTopBar
import com.virtualworld.contadorkm.ui.screen.currentrun.component.MyMaps
import kotlinx.coroutines.delay



@Composable
fun CurrentRunScreen(navController: NavController, viewModel: CurrentRunViewModel = hiltViewModel())
{
    val context = LocalContext.current

    //check the location in the sistem is avilited
    LaunchedEffect(key1 = true) {
        LocationUtils.checkAndRequestLocationSetting(context as Activity)
    }


    //si fnalizo la carrera
    var isRunningFinished by rememberSaveable { mutableStateOf(false) }
    var shouldShowRunningCard by rememberSaveable { mutableStateOf(false) }

    val runState by viewModel.currentRunStateWithCal.collectAsStateWithLifecycle()//start point in empty
    val runningDurationInMillis by viewModel.runningDurationInMillis.collectAsStateWithLifecycle() //start in 0

    //show the card running in 0.5 seconds
    LaunchedEffect(key1 = Unit) {
        delay(500)
        shouldShowRunningCard = true
    }

    Box(modifier = Modifier.fillMaxSize()) {


//my mapa
        MyMaps(pathPoints = runState.currentRunState.pathPoints,//posicion
            isRunningFinished = isRunningFinished,//indicar state the run
            onSnapshot = {
                viewModel.finishRun(it)//indicar vw fnalzar run
                navController.navigateUp()//atras
            })

//flecha back*************
        CurrentRunTopBar(modifier = Modifier
            .align(Alignment.TopStart)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)) {
            navController.navigateUp()//back
        }


//card the play
        CurrentRunBottomCard(visible = shouldShowRunningCard,
                             modifier = Modifier.align(Alignment.BottomCenter),
                             onPlayPauseButtonClick = viewModel::playPauseTracking,
                             runState = runState,
                             durationInMillis = runningDurationInMillis,
                             onFinish = { isRunningFinished = true })
    }
}

