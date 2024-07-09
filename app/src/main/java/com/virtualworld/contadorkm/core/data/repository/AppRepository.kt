package com.virtualworld.contadorkm.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.virtualworld.contadorkm.core.data.db.RunDao
import com.virtualworld.contadorkm.core.data.model.Run
import com.virtualworld.contadorkm.core.data.util.RunSortOrder

import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val runDao: RunDao
) {
    suspend fun insertRun(run: Run) = runDao.insertRun(run)

    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

   /* fun getSortedAllRun(sortingOrder: RunSortOrder) = Pager(
        config = PagingConfig(pageSize = 20),
    ) {
        when (sortingOrder) {
            RunSortOrder.DATE -> runDao.getAllRunSortByDate()
            RunSortOrder.DURATION -> runDao.getAllRunSortByDuration()
            RunSortOrder.AVG_SPEED -> runDao.getAllRunSortByAvgSpeed()
            RunSortOrder.DISTANCE -> runDao.getAllRunSortByDistance()

        }
    }

    */

    fun getTotalDistance(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalDistance(fromDate, toDate)

    fun getMaxDistance(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
    runDao.getMaxDistance(fromDate,toDate)


    fun getAvgDistance(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getAvgDistance(fromDate,toDate)


    fun getTotalTime(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalTime(fromDate, toDate)

    fun getMaxTime(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getMaxTime(fromDate,toDate)


    fun getAvgTime(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getAvgTime(fromDate,toDate)

    fun getMaxSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getMaxSpeed(fromDate,toDate)


    fun getAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getAvgSpeed(fromDate,toDate)





    fun getRunByDescDateWithLimit(limit: Int) = runDao.getRunByDescDateWithLimit(limit)

    fun getTotalRunningDuration(fromDate: Date? = null, toDate: Date? = null): Flow<Long> =
        runDao.getTotalRunningDuration(fromDate, toDate)




    fun getTotalAvgSpeed(fromDate: Date? = null, toDate: Date? = null): Flow<Float> =
        runDao.getTotalAvgSpeed(fromDate, toDate)

}