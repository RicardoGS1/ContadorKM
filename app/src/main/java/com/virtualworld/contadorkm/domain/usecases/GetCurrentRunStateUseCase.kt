package com.virtualworld.contadorkm.domain.usecases


import com.virtualworld.contadorkm.core.location.TrackingManager
import com.virtualworld.contadorkm.domain.model.CurrentRunStateUI
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

    operator fun invoke(): Flow<CurrentRunStateUI>
    {

        return flow {

            trackingManager.currentRunState.collect {
                emit(CurrentRunStateUI(currentRunState = it))
            }

        }
    }

}


