package com.virtualworld.contadorkm

import com.google.android.gms.location.LocationCallback

interface LocationTrackingManager {
    fun registerCallback(locationCallback: LocationCallback)

    fun unRegisterCallback(locationCallback: LocationCallback)
}