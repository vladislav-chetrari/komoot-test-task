package com.komoot.vchetrari.challenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.komoot.vchetrari.challenge.domain.FlowLatestPhotosUseCase
import com.komoot.vchetrari.challenge.domain.FlowLocationTrackingEnabledUseCase
import com.komoot.vchetrari.challenge.presentation.model.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    flowLatestPhotos: FlowLatestPhotosUseCase,
    flowLocationTrackingEnabled: FlowLocationTrackingEnabledUseCase
) : ViewModel() {

    val viewState: StateFlow<MainViewState> = flowLocationTrackingEnabled()
        .map { MainViewState(isLocationTrackingEnabled = it) }
        .combine(flowLatestPhotos()) { state, photos -> state.copy(photos = photos) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainViewState())
}