package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ClearPhotosUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: PhotoRepository

    @InjectMockKs
    private lateinit var useCase: ClearPhotosUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() = runTest {
        useCase()

        coVerify { repository.clearAll() }
    }
}