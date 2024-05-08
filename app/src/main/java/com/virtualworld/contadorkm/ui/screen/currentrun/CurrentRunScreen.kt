package com.virtualworld.contadorkm.ui.screen.currentrun

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

import com.virtualworld.contadorkm.core.location.LocationUtils
import com.virtualworld.contadorkm.R

import com.virtualworld.contadorkm.ui.util.bitmapDescriptorFromVector
import com.virtualworld.contadorkm.ui.theme.ContadorKMTheme

@Composable
@Preview(showBackground = true)
private fun CurrentRunComposable()
{
    ContadorKMTheme {
        Surface {
            CurrentRunScreen(rememberNavController())
        }
    }
}


@Composable
fun CurrentRunScreen(navController: NavController, viewModel: CurrentRunViewModel = hiltViewModel())
{

    viewModel.findLocationCurrent()


//**********************************************************************************************
    //check if localization is activated in the setting
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        LocationUtils.checkAndRequestLocationSetting(context as Activity)
    }
//***********************************************************************************************



    val runState by viewModel.currentRunStateWithCalories.collectAsStateWithLifecycle()




    Box(modifier = Modifier.fillMaxSize()) {

        MyMap(
            pathPoints = runState.latLng,

            )





    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun BoxScope.MyMap(
    modifier: Modifier = Modifier,
    pathPoints: LatLng?,


    )
{




    var mapSize by remember { mutableStateOf(Size(0f, 0f)) }
    var mapCenter by remember { mutableStateOf(Offset(0f, 0f)) }
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {}
    val mapUiSettings by remember {
        mutableStateOf(MapUiSettings(mapToolbarEnabled = false, compassEnabled = true, zoomControlsEnabled = false))
    }



    LaunchedEffect(key1 = pathPoints) {

            if(pathPoints!=null)
            {
                println("changer Point lacation")

                cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(pathPoints, 15f)))
            }


    }


    ShowMapLoadingProgressBar(isMapLoaded)


    GoogleMap(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                mapSize = size
                mapCenter = center
            },
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState,
        onMapLoaded = { isMapLoaded = true },

        ) {

        if (pathPoints != null)
        {
            DrawPathPoints(pathPoints = pathPoints)
        }


    }
}


@Composable
@GoogleMapComposable
private fun DrawPathPoints(
    pathPoints: LatLng,

    )
{
    val lastMarkerState = rememberMarkerState()
    pathPoints.let { lastMarkerState.position = it }




    println("recontrullendo")
    println(pathPoints)




        Marker(icon = bitmapDescriptorFromVector(
            context = LocalContext.current,
            vectorResId = R.drawable.ic_circle,
        ), state = lastMarkerState, anchor = Offset(0.5f, 0.5f))

}


@Composable
private fun BoxScope.ShowMapLoadingProgressBar(isMapLoaded: Boolean = false)
{
    AnimatedVisibility(
        modifier = Modifier.matchParentSize(),
        visible = !isMapLoaded,
        enter = EnterTransition.None,
        exit = fadeOut(),
    ) {
        CircularProgressIndicator(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .wrapContentSize())
    }
}

