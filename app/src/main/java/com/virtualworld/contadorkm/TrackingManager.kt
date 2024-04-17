package com.sdevprem.runtrack.core.tracking

import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng


import com.virtualworld.contadorkm.LocationTrackingManager
import com.virtualworld.contadorkm.PointCurrent
import com.virtualworld.contadorkm.TimeTracker
import com.virtualworld.contadorkm.TrackingServiceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingManager @Inject constructor(private val locationTrackingManager: LocationTrackingManager,
                                          private val timeTracker: TimeTracker,
                                          private val trackingServiceManager: TrackingServiceManager)
{
    private var isTracking = false


    private val _currentRunState = MutableStateFlow(PointCurrent())
    val currentRunState = _currentRunState

    private val _trackingDurationInMs = MutableStateFlow(0L)
    val trackingDurationInMs = _trackingDurationInMs.asStateFlow()

    private val timeTrackerCallback = { timeElapsed: Long ->
        _trackingDurationInMs.update { timeElapsed }
    }

    private var isFirst = true





    fun findLocationCurrent(){

        locationTrackingManager.registerCallback(locationCallback)
    }








    private val locationCallback = object : LocationCallback()
    {
        override fun onLocationResult(result: LocationResult)
        {

            println("locationCallback")


                result.locations.forEach { location ->
                    addPathPoints(location)
                    Timber.d("New LocationPoint : ${location.latitude}, ${location.longitude}")
                    println("New LocationPoint : ${location.latitude}, ${location.longitude}")

                }

        }
    }



    private fun addPathPoints(location: Location?) = location?.let {


        println("addpoint")

        val pos = LatLng(it.latitude, it.longitude)
        _currentRunState.update { state ->

            state.copy( LatLng(it.latitude, it.longitude))


        }
    }













}