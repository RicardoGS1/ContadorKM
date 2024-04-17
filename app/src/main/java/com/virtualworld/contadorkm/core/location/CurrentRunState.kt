package com.virtualworld.contadorkm.core.location

import com.virtualworld.contadorkm.PathPoint

data class CurrentRunState(
    val distanceInMeters: Int = 0,
    val speedInKMH: Float = 0f,
    val isTracking: Boolean = false,
    val pathPoints: List<PathPoint> = emptyList()
)
