package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.UserLocationRepository
import com.komoot.vchetrari.challenge.data.model.UserLocation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FlowUserLocationUseCaseTest {

    @MockK
    private lateinit var repository: UserLocationRepository

    @InjectMockKs
    private lateinit var useCase: FlowUserLocationUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() {
        val flow = mockk<Flow<UserLocation>>()
        every { repository.flowByDistance(100) } returns flow

        assertEquals(flow, useCase())
    }
}