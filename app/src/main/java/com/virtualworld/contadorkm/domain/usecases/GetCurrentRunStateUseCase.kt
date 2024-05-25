package com.virtualworld.contadorkm.domain.usecases


import com.virtualworld.contadorkm.core.location.TrackingManager
import com.virtualworld.contadorkm.domain.model.CurrentRunStateWithCalories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.flow.flowOf


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentRunStateUseCase @Inject constructor(

    private val trackingManager: TrackingManager)
{

    operator fun invoke(): Flow<CurrentRunStateWithCalories>
    {

        return flow {

            trackingManager.currentRunState.collect {
                emit(CurrentRunStateWithCalories(currentRunState = it, caloriesBurnt = 50))
            }

        }
    }

        //caloriesBurnt = RunUtils.calculateCaloriesBurnt(distanceInMeters = trackingManager.currentRunState.value.distanceInMeters,
        //                                              weightInKg = 80f).roundToInt()))

    }


