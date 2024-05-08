package com.virtualworld.contadorkm.domain


import com.virtualworld.contadorkm.core.location.TrackingManager
import com.virtualworld.contadorkm.core.model.PointCurrent

import kotlinx.coroutines.flow.MutableStateFlow


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentRunStateWithCaloriesUseCase @Inject constructor(

    private val trackingManager: TrackingManager)
{



    operator fun invoke(): MutableStateFlow<PointCurrent>
    {





        return trackingManager.currentRunState








    }
}