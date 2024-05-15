package com.virtualworld.contadorkm.ui.screen.currentrun

import android.app.Activity
import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.virtualworld.contadorkm.ComposeUtils

import com.virtualworld.contadorkm.core.location.LocationUtils
import com.virtualworld.contadorkm.R
import com.virtualworld.contadorkm.RunUtils.firstLocationPoint
import com.virtualworld.contadorkm.RunUtils.lasLocationPoint
import com.virtualworld.contadorkm.RunUtils.takeSnapshot
import com.virtualworld.contadorkm.core.model.CurrentRunState
import com.virtualworld.contadorkm.core.model.PathPoint
import com.virtualworld.contadorkm.domain.CurrentRunStateWithCalories


import com.virtualworld.contadorkm.ui.util.bitmapDescriptorFromVector
import kotlinx.coroutines.delay
import java.math.RoundingMode


@Composable
fun CurrentRunScreen(navController: NavController, viewModel: CurrentRunViewModel = hiltViewModel())
{
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        LocationUtils.checkAndRequestLocationSetting(context as Activity)
    }


    var isRunningFinished by rememberSaveable { mutableStateOf(false) }
    var shouldShowRunningCard by rememberSaveable { mutableStateOf(false) }
    val runState by viewModel.currentRunStateWithCalories.collectAsStateWithLifecycle()
    val runningDurationInMillis by viewModel.runningDurationInMillis.collectAsStateWithLifecycle()

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

