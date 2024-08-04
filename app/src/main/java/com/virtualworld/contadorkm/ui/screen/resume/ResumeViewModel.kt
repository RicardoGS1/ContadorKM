package com.virtualworld.contadorkm.ui.screen.resume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualworld.contadorkm.core.data.repository.AppRepository
import com.virtualworld.contadorkm.core.location.model.NetworkResponseState
import com.virtualworld.contadorkm.domain.utils.DateUtils.getFirstDayOfCurrentYear
import com.virtualworld.contadorkm.domain.utils.DateUtils.getFirstDayOfMonth
import com.virtualworld.contadorkm.domain.utils.DateUtils.getFirstDayOfWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(val repository: AppRepository) : ViewModel()
{

    private val _resumeScreenStateTime = MutableStateFlow<ResumeScreenStateTime>(ResumeScreenStateTime.Loading)
    val resumeScreenStateTime: StateFlow<ResumeScreenStateTime> = _resumeScreenStateTime

    private val _resumeScreenStateDistance = MutableStateFlow<ResumeScreenStateDistances>(ResumeScreenStateDistances())
    val resumeScreenStateDistance: StateFlow<ResumeScreenStateDistances> = _resumeScreenStateDistance

    private val _resumeScreenStateSpeed = MutableStateFlow<ResumeScreenStateSpeed>(ResumeScreenStateSpeed.Loading)
    val resumeScreenStateSpeed: StateFlow<ResumeScreenStateSpeed> = _resumeScreenStateSpeed

    private val _fromDate = MutableStateFlow<Date?>(null)
    val fromDate: StateFlow<Date?> = _fromDate


    fun onSelectionChange(text: String)
    {
        _fromDate.update {
            when (text)
            {
                "Semana" -> getFirstDayOfWeek()
                "Mes" -> getFirstDayOfMonth()
                "Ano" -> getFirstDayOfCurrentYear()
                "Todo" -> null
                else -> null // Manejo de casos no esperados
            }
        }
    }


    fun changerFecha(fromDate: Date?)
    {
        getFromTime(fromDate)
        getDistance(fromDate)
        getSpeed(fromDate)
    }

    private fun getSpeed(fromDate: Date?)
    {
        viewModelScope.launch {
            try
            {
                println(fromDate)

                combine(
                    repository.getMaxSpeed(fromDate),
                    repository.getAvgSpeed(fromDate),

                    ) { maxSpeed, avgSpeed ->

                    ResumeScreenStateSpeed.Success(speedMax = maxSpeed, speedAvg = avgSpeed)


                }.collect {
                    _resumeScreenStateSpeed.value = it
                }


            } catch (e: Exception)
            {
                _resumeScreenStateSpeed.value = ResumeScreenStateSpeed.Error
            }

        }
    }

    //**************************************************************************************************************
    private fun getDistance(fromDate: Date?)
    {


        repository.getDistances(fromDate).onEach { state ->
            when (state)
            {

                is NetworkResponseState.Loading -> println("Loading")
                is NetworkResponseState.Error -> println("Error")
                is NetworkResponseState.Success ->
                {

                    _resumeScreenStateDistance.update {
                        it.copy(distanceTotal = state.result.distanceTotal, distanceAvg = state.result.distanceAvg, distanceMax = state.result.distanceMax)
                    }


                }
            }
        }.launchIn(viewModelScope)


    }


//*********************************************************************************************************************
    /*
          viewModelScope.launch {
              try
              {
                  println(fromDate)

                  combine(
                      repository.getTotalDistance(fromDate),
                      repository.getMaxDistance(fromDate),
                      repository.getAvgDistance(fromDate),
                  ) { totalDistance, maxDistance, avgDistance ->

                      ResumeScreenStateDistance.Success(distanceTotal = totalDistance, distanceMax = maxDistance, distanceAvg = avgDistance)


                  }.collect {
                      _resumeScreenStateDistance.value = it
                  }


              } catch (e: Exception)
              {
                  _resumeScreenStateDistance.value = ResumeScreenStateDistance.Error
              }

          }


     */


    private fun getFromTime(fromDate: Date?)
    {

        viewModelScope.launch {
            try
            {
                println(fromDate)

                combine(
                    repository.getTotalTime(fromDate),
                    repository.getMaxTime(fromDate),
                    repository.getAvgTime(fromDate),
                ) { totalDistance, maxDistance, avgDistance ->

                    ResumeScreenStateTime.Success(timeTotal = totalDistance, timeMax = maxDistance, timeAvg = avgDistance)


                }.collect {
                    _resumeScreenStateTime.value = it
                }


            } catch (e: Exception)
            {
                _resumeScreenStateTime.value = ResumeScreenStateTime.Error
            }

        }


    }
}
