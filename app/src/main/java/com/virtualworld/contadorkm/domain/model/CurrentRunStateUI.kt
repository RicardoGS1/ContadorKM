package com.virtualworld.contadorkm.domain.model


import com.virtualworld.contadorkm.core.location.model.CurrentRunState

data class CurrentRunStateUI(
    val currentRunState: CurrentRunState = CurrentRunState(),
    //futuros estados de la carrera
)