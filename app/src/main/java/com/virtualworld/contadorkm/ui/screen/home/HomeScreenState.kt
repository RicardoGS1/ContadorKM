package com.virtualworld.contadorkm.ui.screen.home


import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.domain.model.CurrentRunStateWithCalories

data class HomeScreenState(
    val runList: List<Run> = emptyList(),
    val currentRunStateWithCalories: CurrentRunStateWithCalories = CurrentRunStateWithCalories(),
    val currentRunInfo: Run? = null,
  //  val user: User = User(),
    val distanceCoveredInKmInThisWeek: Float = 0.0f
)
