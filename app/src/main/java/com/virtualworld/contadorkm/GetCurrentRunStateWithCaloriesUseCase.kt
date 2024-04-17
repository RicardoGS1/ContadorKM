package com.virtualworld.contadorkm


import androidx.compose.runtime.mutableFloatStateOf
import com.sdevprem.runtrack.core.data.repository.UserRepository
import com.sdevprem.runtrack.core.tracking.TrackingManager

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update


import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class GetCurrentRunStateWithCaloriesUseCase @Inject constructor(

    private val trackingManager: TrackingManager)
{



    operator fun invoke(): MutableStateFlow<PointCurrent>
    {





        return trackingManager.currentRunState








    }
}