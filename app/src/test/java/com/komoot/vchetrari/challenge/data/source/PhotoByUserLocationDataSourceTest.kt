package com.komoot.vchetrari.challenge.data.source

import com.komoot.vchetrari.challenge.data.model.UserLocation
import com.komoot.vchetrari.challenge.data.model.response.PhotoResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class PhotoByUserLocationDataSourceTest {

    @MockK
    private lateinit var client: PhotoSearchClient

    @InjectMockKs
    private lateinit var dataSource: PhotoByUserLocationDataSource

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() {
        val userLocation = UserLocation(
            latitude = 1.1,
            longitude = 2.2,
            distanceFromLastLocationMeters = 314
        )
        val photoResponse = PhotoResponse(
            id = "photoId",
            secret = "shhh",
            serverId = "boring-id"
        )
        coEvery {
            client.getByCoordinates(1.1, 2.2, .157)
        } returns mockk {
            every { photos.value } returns listOf(photoResponse)
        }

        runTest {
            val result = dataSource(userLocation)
            assertNotNull(result)
            assertEquals("photoId", result!!.id)
            assertEquals("https://live.staticflickr.com/boring-id/photoId_shhh_b.jpg", result.url)
        }
    }

    @Test
    fun `invoke WHEN response has no photos THEN returns null`() {
        val userLocation = UserLocation(
            latitude = 1.1,
            longitude = 2.2,
            distanceFromLastLocationMeters = 314
        )
        coEvery {
            client.getByCoordinates(1.1, 2.2, .157)
        } returns mockk {
            every { photos.value } returns emptyList()
        }

        runTest {
            assertNull(dataSource(userLocation))
        }
    }
}