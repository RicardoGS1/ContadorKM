package com.virtualworld.contadorkm.core.location.model

import com.google.android.gms.maps.model.LatLng

sealed interface PathPoint {
    class LocationPoint(val latLng: LatLng) : PathPoint
    object EmptyLocationPoint : PathPoint
}