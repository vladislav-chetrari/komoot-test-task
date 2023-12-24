package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.PhotoRepository
import com.komoot.vchetrari.challenge.data.model.Photo
import com.komoot.vchetrari.challenge.data.model.UserLocation
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddImageByCoordinatesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: PhotoRepository

    @InjectMockKs
    private lateinit var useCase: AddImageByCoordinatesUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() = runTest {
        val location = mockk<UserLocation>()
        val photo = mockk<Photo>()
        coEvery { repository.getByLocation(location) } returns photo

        useCase(location)

        coVerify { repository.save(photo) }
    }

    @Test
    fun `invoke WHEN getByLocation returns null photo THEN doesn't save it`() = runTest {
        val location = mockk<UserLocation>()
        coEvery { repository.getByLocation(location) } returns null

        useCase(location)

        coVerify(exactly = 0) { repository.save(any()) }
    }
}