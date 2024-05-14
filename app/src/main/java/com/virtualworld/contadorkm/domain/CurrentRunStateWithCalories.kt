package com.virtualworld.contadorkm.domain


import com.virtualworld.contadorkm.core.model.CurrentRunState

data class CurrentRunStateWithCalories(
    val currentRunState: CurrentRunState = CurrentRunState(),
    val caloriesBurnt: Int = 0
)