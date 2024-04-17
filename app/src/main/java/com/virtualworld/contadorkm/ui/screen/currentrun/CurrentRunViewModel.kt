package com.virtualworld.contadorkm.ui.screen.currentrun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdevprem.runtrack.core.tracking.TrackingManager

import com.virtualworld.contadorkm.GetCurrentRunStateWithCaloriesUseCase
import com.virtualworld.contadorkm.PointCurrent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CurrentRunViewModel @Inject constructor(getCurrentRunStateWithCaloriesUseCase: GetCurrentRunStateWithCaloriesUseCase, private val trackingManager: TrackingManager,) : ViewModel()
{


    val currentRunStateWithCalories = getCurrentRunStateWithCaloriesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, PointCurrent())


    fun findLocationCurrent() {
       trackingManager.findLocationCurrent()
    }


}