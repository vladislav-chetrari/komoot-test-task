package com.komoot.vchetrari.challenge.data.source

import android.os.Looper
import app.cash.turbine.turbineScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserLocationDataSourceTest {

    @MockK
    private lateinit var looper: Looper

    @RelaxedMockK
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @InjectMockKs
    private lateinit var dataSource: UserLocationDataSource

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() = runTest {
        turbineScope {
            val distanceMeters = 50
            val turbine = dataSource(distanceMeters).testIn(backgroundScope)
            val listenerSlot = slot<LocationListener>()
            verify {
                fusedLocationProviderClient.requestLocationUpdates(
                    match<LocationRequest> {
                        it.intervalMillis == 10_000L &&
                                it.priority == Priority.PRIORITY_HIGH_ACCURACY &&
                                it.minUpdateDistanceMeters == distanceMeters.toFloat()
                    },
                    capture(listenerSlot),
                    looper
                )
            }
            assertTrue(listenerSlot.isCaptured)
            val listener = listenerSlot.captured

            listener.onLocationChanged(mockk {
                every { latitude } returns 1.0
                every { longitude } returns 2.0
            })
            turbine.awaitItem().let {
                assertEquals(1.0, it.latitude, .0)
                assertEquals(2.0, it.longitude, .0)
                assertEquals(50, it.distanceFromLastLocationMeters)
            }

            listener.onLocationChanged(mockk {
                every { latitude } returns 2.0
                every { longitude } returns 3.0
            })
            turbine.awaitItem().let {
                assertEquals(2.0, it.latitude, .0)
                assertEquals(3.0, it.longitude, .0)
                assertEquals(50, it.distanceFromLastLocationMeters)
            }

            turbine.cancel()
            verify { fusedLocationProviderClient.removeLocationUpdates(listener) }
        }
    }
}