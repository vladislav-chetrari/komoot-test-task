package com.komoot.vchetrari.challenge.presentation

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.komoot.vchetrari.challenge.data.model.UserLocation
import com.komoot.vchetrari.challenge.domain.AddImageByCoordinatesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class AddImageByCoordinatesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val addImageByCoordinates: AddImageByCoordinatesUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        addImageByCoordinates(
            UserLocation(
                latitude = inputData.getDouble(ARG_LATITUDE, .0),
                longitude = inputData.getDouble(ARG_LONGITUDE, .0),
                distanceFromLastLocationMeters = inputData.getInt(ARG_DISTANCE_FROM_LAST_LOCATION_METERS, 0)
            )
        )
        return Result.success()
    }

    companion object {
        private const val BACKOFF_SECONDS = 30L
        private const val ARG_LATITUDE = "lat"
        private const val ARG_LONGITUDE = "lon"
        private const val ARG_DISTANCE_FROM_LAST_LOCATION_METERS = "distanceFromLastLocationMeters"

        fun oneTimeRequest(userLocation: UserLocation): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<AddImageByCoordinatesWorker>()
                .setConstraints(Constraints(requiredNetworkType = NetworkType.CONNECTED))
                .setBackoffCriteria(BackoffPolicy.LINEAR, BACKOFF_SECONDS, TimeUnit.SECONDS)
                .setInputData(
                    //doesn't support parcelables :(
                    workDataOf(
                        ARG_LATITUDE to userLocation.latitude,
                        ARG_LONGITUDE to userLocation.longitude,
                        ARG_DISTANCE_FROM_LAST_LOCATION_METERS to userLocation.distanceFromLastLocationMeters
                    )
                )
                .build()
    }
}