package com.komoot.vchetrari.challenge.data

import app.cash.turbine.test
import com.komoot.vchetrari.challenge.data.model.UserLocation
import com.komoot.vchetrari.challenge.data.source.UserLocationDataSource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserLocationRepositoryTest {

    @MockK
    private lateinit var userLocationDataSource: UserLocationDataSource

    @InjectMockKs
    private lateinit var repository: UserLocationRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun flowByDistance() {
        val distanceMeters = 1
        val flow = mockk<Flow<UserLocation>>()
        every { userLocationDataSource(distanceMeters) } returns flow

        assertEquals(flow, repository.flowByDistance(distanceMeters))
    }

    @Test
    fun isTrackingEnabled() = runTest {
        repository.isTrackingEnabled().test {
            assertFalse(awaitItem())

            repository.updateTrackingEnabled(true)
            assertTrue(awaitItem())
        }
    }
}