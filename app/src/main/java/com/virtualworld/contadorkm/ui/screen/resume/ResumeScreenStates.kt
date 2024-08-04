package com.virtualworld.contadorkm.ui.screen.resume

data class ResumeScreenStateDistances(
    val name: String = "Distancia",
    val distanceTotal: Long = 0,
    val distanceMax: Long = 0,
    val distanceAvg: Long = 0,
)

data class ResumeScreenStateTimes(
    val name: String = "Tiempo",
    val timeTotal: Long,
    val timeMax: Long,
    val timeAvg: Long,
)

data class ResumeScreenStateSpeeds(
    val name: String = "Velocidad",
    val speedMax: Long,
    val speedAvg: Long,
)