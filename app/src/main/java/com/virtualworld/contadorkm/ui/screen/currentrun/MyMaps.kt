package com.virtualworld.contadorkm.ui.screen.currentrun

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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
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
import com.virtualworld.contadorkm.R
import com.virtualworld.contadorkm.RunUtils
import com.virtualworld.contadorkm.RunUtils.firstLocationPoint
import com.virtualworld.contadorkm.RunUtils.lasLocationPoint
import com.virtualworld.contadorkm.core.model.PathPoint
import com.virtualworld.contadorkm.ui.util.bitmapDescriptorFromVector



@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun BoxScope.MyMaps(
    modifier: Modifier = Modifier,
    pathPoints: List<PathPoint>,
    isRunningFinished: Boolean,
    onSnapshot: (Bitmap) -> Unit,
)
{
    var mapSize by remember { mutableStateOf(Size(0f, 0f)) }
    var mapCenter by remember { mutableStateOf(Offset(0f, 0f)) }
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {}
    val mapUiSettings by remember {
        mutableStateOf(MapUiSettings(mapToolbarEnabled = false, compassEnabled = true, zoomControlsEnabled = false))
    }

    LaunchedEffect(key1 = pathPoints.lasLocationPoint()) {
        pathPoints.lasLocationPoint()?.let {
            cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(it.latLng, 15f)))
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

        DrawPathPoints(pathPoints = pathPoints, isRunningFinished = isRunningFinished)

        MapEffect(key1 = isRunningFinished) { map ->
            if (isRunningFinished) RunUtils.takeSnapshot(map, pathPoints, mapCenter, onSnapshot, snapshotSideLength = mapSize.width / 2)
        }
    }
}

@Composable
@GoogleMapComposable
private fun DrawPathPoints(
    pathPoints: List<PathPoint>,
    isRunningFinished: Boolean,
)
{
    val lastMarkerState = rememberMarkerState()
    val lastLocationPoint = pathPoints.lasLocationPoint()
    lastLocationPoint?.let { lastMarkerState.position = it.latLng }

    val firstLocationPoint = pathPoints.firstLocationPoint()
    val firstPoint = remember(key1 = firstLocationPoint) { firstLocationPoint }

    val latLngList = mutableListOf<LatLng>()

    pathPoints.forEach { pathPoint ->
        if (pathPoint is PathPoint.EmptyLocationPoint)
        {
            Polyline(
                points = latLngList.toList(),
                color = md_theme_light_primary,
            )
            latLngList.clear()
        } else if (pathPoint is PathPoint.LocationPoint)
        {
            latLngList += pathPoint.latLng
        }
    }

    //add the last path points
    if (latLngList.isNotEmpty()) Polyline(points = latLngList.toList(), color = md_theme_light_primary)

    val infiniteTransition = rememberInfiniteTransition()
    val lastMarkerPointColor by infiniteTransition.animateColor(initialValue = md_theme_light_primary,
                                                                targetValue = md_theme_light_primary.copy(alpha = 0.8f),
                                                                animationSpec = infiniteRepeatable(tween(1000), repeatMode = RepeatMode.Reverse))

    Marker(icon = bitmapDescriptorFromVector(context = LocalContext.current,
                                             vectorResId = R.drawable.ic_circle,
                                             tint = (if (isRunningFinished) md_theme_light_primary else lastMarkerPointColor).toArgb()),
           state = lastMarkerState,
           anchor = Offset(0.5f, 0.5f),
           visible = lastLocationPoint != null)

    firstPoint?.let {
        Marker(icon = bitmapDescriptorFromVector(context = LocalContext.current,
                                                 vectorResId = if (isRunningFinished) R.drawable.ic_circle else R.drawable.ic_circle_hollow,
                                                 tint = if (isRunningFinished) md_theme_light_primary.toArgb() else null),
               state = rememberMarkerState(position = it.latLng),
               anchor = Offset(0.5f, 0.5f))
    }
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
