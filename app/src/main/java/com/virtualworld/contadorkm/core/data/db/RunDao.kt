package com.virtualworld.contadorkm.core.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.virtualworld.contadorkm.core.data.model.Run
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)


//Distace****************************************************************************
    @Query(
        "SELECT TOTAL(distanceInMeters) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalDistance(fromDate: Date?, toDate: Date?): Flow<Long>


    @Query(
        "SELECT MAX(distanceInMeters) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getMaxDistance(fromDate: Date?, toDate: Date?): Flow<Long>


    @Query(
        "SELECT AVG(distanceInMeters) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getAvgDistance(fromDate: Date?, toDate: Date?): Flow<Long>
    
//************************************************************************************************

    @Query(
        "SELECT TOTAL(durationInMillis) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalTime(fromDate: Date?, toDate: Date?): Flow<Long>


    @Query(
        "SELECT MAX(durationInMillis) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getMaxTime(fromDate: Date?, toDate: Date?): Flow<Long>


    @Query(
        "SELECT AVG(durationInMillis) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getAvgTime(fromDate: Date?, toDate: Date?): Flow<Long>


//**************************************************************************************

    @Query(
        "SELECT MAX(avgSpeedInKMH) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getMaxSpeed(fromDate: Date?, toDate: Date?): Flow<Long>


    @Query(
        "SELECT AVG(avgSpeedInKMH) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getAvgSpeed(fromDate: Date?, toDate: Date?): Flow<Long>


//**************************************************************************************




    @Query("SELECT * FROM running_table ORDER BY timestamp DESC")
    fun getAllRunSortByDate(): PagingSource<Int, Run>

    @Query("SELECT * FROM running_table ORDER BY durationInMillis DESC")
    fun getAllRunSortByDuration(): PagingSource<Int, Run>


    @Query("SELECT * FROM running_table ORDER BY avgSpeedInKMH DESC")
    fun getAllRunSortByAvgSpeed(): PagingSource<Int, Run>

  //  @Query("SELECT * FROM running_table ORDER BY distanceInMeters DESC")
 //   fun getAllRunSortByDistance():Flow<Long>

    @Query("SELECT * FROM running_table ORDER BY timestamp DESC LIMIT :limit")
    fun getRunByDescDateWithLimit(limit: Int): Flow<List<Run>>


    //for statistics
    @Query(
        "SELECT TOTAL(durationInMillis) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalRunningDuration(fromDate: Date?, toDate: Date?): Flow<Long>






    @Query(
        "SELECT AVG(avgSpeedInKMH) FROM running_table WHERE " +
                "(:fromDate IS NULL OR timestamp >= :fromDate) AND " +
                "(:toDate IS NULL OR timestamp <= :toDate) " +
                "ORDER BY timestamp DESC"
    )
    fun getTotalAvgSpeed(fromDate: Date?, toDate: Date?): Flow<Float>

}