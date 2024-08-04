package com.virtualworld.contadorkm.core.data.repository

import com.virtualworld.contadorkm.core.data.db.RunDao
import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.core.location.model.NetworkResponseState
import com.virtualworld.contadorkm.ui.screen.resume.ResumeScreenStateDistances

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val runDao: RunDao)
{
    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    //**************************************************************************************
    fun getDistances(fromDate: Date? = null, toDate: Date? = null): Flow<NetworkResponseState<ResumeScreenStateDistances>>
    {

        return flow {
            emit(NetworkResponseState.Loading)
            try
            {
                val distanceTotal = runDao.getTotalDistance(fromDate, toDate)
                val distanceMax = runDao.getMaxDistance(fromDate, toDate)
                val distanceAvg = runDao.getAvgDistance(fromDate, toDate)

                emit(NetworkResponseState.Success(ResumeScreenStateDistances(
                    distanceTotal = distanceTotal,
                    distanceMax = distanceMax,
                    distanceAvg = distanceAvg,
                )))

            } catch (e: Exception)
            {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    //******************************************************************************************


    fun getTotalTime(fromDate: Date? = null, toDate: Date? = null): Flow<Long> = runDao.getTotalTime(fromDate, toDate)

    fun getMaxTime(fromDate: Date? = null, toDate: Date? = null): Flow<Long> = runDao.getMaxTime(fromDate, toDate)


    fun getAvgTime(fromDate: Date? = null, toDate: Date? = null): Flow<Long> = runDao.getAvgTime(fromDate, toDate)

    //************************************************************************************

    fun getMaxSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Long> = runDao.getMaxSpeed(fromDate, toDate)


    fun getAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Long> = runDao.getAvgSpeed(fromDate, toDate)

    //*************************************************************************************


    fun getRunByDescDateWithLimit(limit: Int) = runDao.getRunByDescDateWithLimit(limit)

    fun getTotalRunningDuration(fromDate: Date? = null, toDate: Date? = null): Flow<Long> = runDao.getTotalRunningDuration(fromDate, toDate)


    fun getTotalAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Float> = runDao.getTotalAvgSpeed(fromDate, toDate)

}