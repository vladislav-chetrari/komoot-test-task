package com.komoot.vchetrari.challenge.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.work.WorkManager
import com.komoot.vchetrari.challenge.R
import com.komoot.vchetrari.challenge.domain.ClearPhotosUseCase
import com.komoot.vchetrari.challenge.domain.FlowUserLocationUseCase
import com.komoot.vchetrari.challenge.domain.UpdateLocationTrackingEnabledUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationTrackingBackgroundService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val workManager: WorkManager by lazy { WorkManager.getInstance(this) }
    private val notificationManager: NotificationManager by lazy { getSystemService()!! }

    @Inject
    internal lateinit var clearPhotos: ClearPhotosUseCase

    @Inject
    internal lateinit var flowUserLocation: FlowUserLocationUseCase

    @Inject
    internal lateinit var updateLocationTrackingEnabled: UpdateLocationTrackingEnabledUseCase

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        updateLocationTrackingEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    getString(R.string.notification_tracking_channel),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_tracking_ongoing_message))
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setOngoing(true)
            .build()

        serviceScope.launch {
            clearPhotos()
            flowUserLocation()
                .onStart { startForeground(NOTIFICATION_ID, notification) }
                .map { AddImageByCoordinatesWorker.oneTimeRequest(it) }
                .collect { workManager.enqueue(it) }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        updateLocationTrackingEnabled(false)
        serviceScope.cancel()
        super.onDestroy()
    }

    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "locationTrackingNotificationChannel"
        const val NOTIFICATION_ID = 314
    }
}