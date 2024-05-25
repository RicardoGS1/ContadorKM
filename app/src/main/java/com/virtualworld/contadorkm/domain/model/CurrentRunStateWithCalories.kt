package com.virtualworld.contadorkm.domain.model


import com.virtualworld.contadorkm.core.location.model.CurrentRunState

data class CurrentRunStateWithCalories(
    val currentRunState: CurrentRunState = CurrentRunState(),
    val caloriesBurnt: Int = 0
)