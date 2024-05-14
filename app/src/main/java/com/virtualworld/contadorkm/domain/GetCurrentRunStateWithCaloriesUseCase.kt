package com.virtualworld.contadorkm.domain


import com.virtualworld.contadorkm.core.location.TrackingManager
import com.virtualworld.contadorkm.core.model.PointCurrent
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCurrentRunStateWithCaloriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val trackingManager: TrackingManager
) {
    operator fun invoke(): Flow<CurrentRunStateWithCalories>
    {
        return combine(userRepository.user, trackingManager.currentRunState) { user, runState ->
            CurrentRunStateWithCalories(
                currentRunState = runState,
                caloriesBurnt = RunUtils.calculateCaloriesBurnt(
                    distanceInMeters = runState.distanceInMeters,
                    weightInKg = user.weightInKg
                ).roundToInt()
            )
        }
    }
}