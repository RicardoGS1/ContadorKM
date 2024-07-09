package com.virtualworld.contadorkm.ui.screen.resume


sealed class ResumeScreenStateTime {
    object Loading : ResumeScreenStateTime()
    data class Success( val name: String = "Tiempo" , val timeTotal: Long, val timeMax: Long, val timeAvg: Long) : ResumeScreenStateTime()
    object Error : ResumeScreenStateTime()
}

sealed class ResumeScreenStateDistance {
    object Loading : ResumeScreenStateDistance()
    data class Success( val name: String = "Distancia" , val distanceTotal: Long, val distanceMax: Long, val distanceAvg: Long) : ResumeScreenStateDistance()
    object Error : ResumeScreenStateDistance()
}


sealed class ResumeScreenStateSpeed {
    object Loading : ResumeScreenStateSpeed()
    data class Success( val name: String = "Velocidad" , val speedMax: Long, val speedAvg: Long) : ResumeScreenStateSpeed()
    object Error : ResumeScreenStateSpeed()
}



