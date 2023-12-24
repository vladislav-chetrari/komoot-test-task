package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.PhotoRepository
import com.komoot.vchetrari.challenge.data.model.Photo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FlowLatestPhotosUseCaseTest {

    @MockK
    private lateinit var repository: PhotoRepository

    @InjectMockKs
    private lateinit var useCase: FlowLatestPhotosUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() {
        val flow = mockk<Flow<List<Photo>>>()
        every { repository.getAllByDateDescending() } returns flow

        assertEquals(flow, useCase())
    }
}