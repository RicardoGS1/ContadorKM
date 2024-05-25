package com.virtualworld.contadorkm.id

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.virtualworld.contadorkm.core.data.RunTrackDB
import com.virtualworld.contadorkm.core.data.RunTrackDB.Companion.RUN_TRACK_DB_NAME
import com.virtualworld.contadorkm.core.location.DefaultLocationTrackingManager
import com.virtualworld.contadorkm.core.location.DefaultTrackingServiceManager

import com.virtualworld.contadorkm.core.location.LocationTrackingManager

import com.virtualworld.contadorkm.core.location.LocationUtils
import com.virtualworld.contadorkm.core.location.TrackingServiceManager

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule
{

    companion object
    {


        //base de datos local

        @Provides
        @Singleton
        fun provideRunningDB(
            @ApplicationContext context: Context
        ): RunTrackDB = Room.databaseBuilder(
            context,
            RunTrackDB::class.java,
            RUN_TRACK_DB_NAME
        ).build()

        @Singleton
        @Provides
        fun provideRunDao(db: RunTrackDB) = db.getRunDao()

        //fin base dato local


        private const val USER_PREFERENCES_FILE_NAME = "user_preferences"


        @Provides
        @Singleton
        fun providesPreferenceDataStore(@ApplicationContext context: Context,
                                        @ApplicationScope scope: CoroutineScope,
                                        @IoDispatcher ioDispatcher: CoroutineDispatcher): DataStore<Preferences> = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES_FILE_NAME) },
            scope = scope.plus(ioDispatcher + SupervisorJob()))


        @Singleton
        @Provides
        fun provideLocationTrackingManager(
            @ApplicationContext context: Context,
            fusedLocationProviderClient: FusedLocationProviderClient,
        ): LocationTrackingManager
        {
            return DefaultLocationTrackingManager(fusedLocationProviderClient = fusedLocationProviderClient,
                                                  context = context,
                                                  locationRequest = LocationUtils.locationRequestBuilder.build())
        }

        @Singleton
        @Provides
        fun provideFusedLocationProviderClient(@ApplicationContext context: Context) = LocationServices.getFusedLocationProviderClient(context)









    }
    @Binds
    @Singleton
    abstract fun provideTrackingServiceManager(
        trackingServiceManager: DefaultTrackingServiceManager
    ): TrackingServiceManager


}