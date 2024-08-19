package com.virtualworld.contadorkm.ui.screen.resume.resumeState

sealed class TimeRange(val description: String) {
    object Week : TimeRange("Semana")
    object Month : TimeRange("Mes")
    object Year : TimeRange("Año")
    object All : TimeRange("Todo")

    companion object {
        fun valores() = listOf(Week, Month, Year, All)
    }


}


