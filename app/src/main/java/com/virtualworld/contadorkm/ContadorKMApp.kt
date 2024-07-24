package com.virtualworld.contadorkm

import android.app.Application
import com.virtualworld.contadorkm.core.notification.NotificationHelper

import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ContadorKMApp : Application(){
    @Inject
    lateinit var notificationHelper: NotificationHelper
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        notificationHelper.createNotificationChannel()
    }
}