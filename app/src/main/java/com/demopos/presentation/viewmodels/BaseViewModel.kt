package com.demopos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel<UiState, UiEvent> : ViewModel() {
    
    protected val _uiState = MutableStateFlow<UiState?>(null)
    val uiState: StateFlow<UiState?> = _uiState.asStateFlow()
    
    protected val _events = MutableStateFlow<UiEvent?>(null)
    val events: StateFlow<UiEvent?> = _events.asStateFlow()
    
    protected fun updateState(newState: UiState) {
        _uiState.value = newState
    }
    
    protected fun emitEvent(event: UiEvent) {
        _events.value = event
    }
    
    protected fun clearEvent() {
        _events.value = null
    }
}
