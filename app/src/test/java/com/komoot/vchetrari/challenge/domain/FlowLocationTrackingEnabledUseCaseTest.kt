package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.UserLocationRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FlowLocationTrackingEnabledUseCaseTest {

    @MockK
    private lateinit var repository: UserLocationRepository

    @InjectMockKs
    private lateinit var useCase: FlowLocationTrackingEnabledUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() {
        val flow = mockk<Flow<Boolean>>()
        every { repository.isTrackingEnabled() } returns flow

        assertEquals(flow, useCase())
    }
}