package com.virtualworld.contadorkm.ui.screen.currentrun

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.core.data.repository.AppRepository
import com.virtualworld.contadorkm.core.location.TrackingManager
import com.virtualworld.contadorkm.domain.model.CurrentRunStateUI

import com.virtualworld.contadorkm.domain.usecases.GetCurrentRunStateUseCase
import com.virtualworld.contadorkm.id.ApplicationScope
import com.virtualworld.contadorkm.id.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CurrentRunViewModel @Inject constructor(private val trackingManager: TrackingManager,
                                              private val repository: AppRepository,
                                              @ApplicationScope private val appCoroutineScope: CoroutineScope,
                                              @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                              getCurrentRunStateUseCase: GetCurrentRunStateUseCase) : ViewModel()
{


    //convierte en un StateFlow el flow
    val currentRunStateWithCal = getCurrentRunStateUseCase().stateIn(viewModelScope, SharingStarted.Lazily, CurrentRunStateUI())

    val runningDurationInMillis = trackingManager.trackingDurationInMs

    fun playPauseTracking()
    {
        if (currentRunStateWithCal.value.currentRunState.isTracking) trackingManager.pauseTracking()
        else trackingManager.startResumeTracking()
    }

    fun finishRun(bitmap: Bitmap)
    {
        trackingManager.pauseTracking()

        saveRun(Run(img = bitmap,
                    avgSpeedInKMH = currentRunStateWithCal.value.currentRunState.distanceInMeters.toBigDecimal().multiply(3600.toBigDecimal())
                        .divide(runningDurationInMillis.value.toBigDecimal(), 2, RoundingMode.HALF_UP).toFloat(),
                    distanceInMeters = currentRunStateWithCal.value.currentRunState.distanceInMeters,
                    durationInMillis = runningDurationInMillis.value,
                    timestamp = Date()))

        trackingManager.stop()
    }

    private fun saveRun(run: Run) = appCoroutineScope.launch(ioDispatcher) {
        repository.insertRun(run)
    }


}