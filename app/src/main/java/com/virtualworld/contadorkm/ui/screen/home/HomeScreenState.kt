package com.virtualworld.contadorkm.ui.screen.home


import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.domain.model.CurrentRunStateUI

data class HomeScreenState(
    val runList: List<Run> = emptyList(),
    val currentRunStateUI: CurrentRunStateUI = CurrentRunStateUI(),
    val currentRunInfo: Run? = null,


    )
