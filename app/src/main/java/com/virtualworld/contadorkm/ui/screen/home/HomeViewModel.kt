package com.virtualworld.contadorkm.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.core.data.repository.AppRepository
import com.virtualworld.contadorkm.core.location.TrackingManager
import com.virtualworld.contadorkm.domain.usecases.GetCurrentRunStateUseCase
import com.virtualworld.contadorkm.id.ApplicationScope
import com.virtualworld.contadorkm.id.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository,
                                        trackingManager: TrackingManager,
                                        @ApplicationScope private val externalScope: CoroutineScope,
                                        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
                                        getCurrentRunStateUseCase: GetCurrentRunStateUseCase) : ViewModel()
{

    val durationInMillis = trackingManager.trackingDurationInMs

    private val _homeScreenState = MutableStateFlow(HomeScreenState())

    /*
    copia en _homeScreenState las combinaciones de los flows que combina en
     repository.getRunByDescDateWithLimit(3) y getCurrentRunStateUseCase()
    */
    val homeScreenState = combine(
        repository.getRunByDescDateWithLimit(3),
        getCurrentRunStateUseCase(),
        _homeScreenState,
    ) { runList, runState, state ->
        state.copy(runList = runList, currentRunStateUI = runState)

    }.stateIn(viewModelScope, SharingStarted.Lazily, HomeScreenState())


    fun deleteRun(run: Run) = externalScope.launch(ioDispatcher) {
        dismissRunDialog()
        repository.deleteRun(run)
    }

    fun showRun(run: Run)
    {
        _homeScreenState.update { it.copy(currentRunInfo = run) }
    }

    fun dismissRunDialog()
    {
        _homeScreenState.update { it.copy(currentRunInfo = null) }
    }

}