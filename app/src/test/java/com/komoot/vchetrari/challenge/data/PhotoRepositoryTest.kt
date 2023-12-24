package com.komoot.vchetrari.challenge.data

import com.komoot.vchetrari.challenge.data.model.Photo
import com.komoot.vchetrari.challenge.data.model.UserLocation
import com.komoot.vchetrari.challenge.data.source.PhotoByUserLocationDataSource
import com.komoot.vchetrari.challenge.data.source.PhotoDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PhotoRepositoryTest {

    @MockK
    private lateinit var photoByUserLocationDataSource: PhotoByUserLocationDataSource

    @RelaxedMockK
    private lateinit var photoDao: PhotoDao

    @InjectMockKs
    private lateinit var repository: PhotoRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getByLocation() = runTest {
        val userLocation = mockk<UserLocation>()
        val photo = mockk<Photo>()
        coEvery { photoByUserLocationDataSource(userLocation) } returns photo

        assertEquals(photo, repository.getByLocation(userLocation))
    }

    @Test
    fun clearAll() = runTest {
        repository.clearAll()

        coVerify { photoDao.deleteAll() }
    }

    @Test
    fun save() = runTest {
        val photo = mockk<Photo>()

        repository.save(photo)

        coVerify { photoDao.save(photo) }
    }

    @Test
    fun getAllByDateDescending() {
        val flow = mockk<Flow<List<Photo>>>()
        every { photoDao.getAllLatest() } returns flow

        assertEquals(flow, repository.getAllByDateDescending())
    }
}