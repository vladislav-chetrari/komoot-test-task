package com.komoot.vchetrari.challenge.domain

import com.komoot.vchetrari.challenge.data.UserLocationRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class UpdateLocationTrackingEnabledUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: UserLocationRepository

    @InjectMockKs
    private lateinit var useCase: UpdateLocationTrackingEnabledUseCase

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun invoke() {
        useCase(true)

        verify { repository.updateTrackingEnabled(true) }
    }
}