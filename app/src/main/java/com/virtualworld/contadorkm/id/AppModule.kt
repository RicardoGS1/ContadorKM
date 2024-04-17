package com.virtualworld.contadorkm.id

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sdevprem.runtrack.core.tracking.location.DefaultLocationTrackingManager
import com.sdevprem.runtrack.core.tracking.notification.DefaultNotificationHelper
import com.sdevprem.runtrack.core.tracking.notification.NotificationHelper
import com.virtualworld.contadorkm.DefaultTrackingServiceManager
import com.virtualworld.contadorkm.LocationTrackingManager
import com.virtualworld.contadorkm.TrackingServiceManager
import com.virtualworld.contadorkm.core.location.LocationUtils

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

    @Binds
    @Singleton
    abstract fun provideNotificationHelper(
        notificationHelper: DefaultNotificationHelper
    ): NotificationHelper

}