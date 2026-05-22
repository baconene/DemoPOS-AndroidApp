package com.demopos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demopos.domain.repositories.AuthRepository
import com.demopos.presentation.state.AuthEvent
import com.demopos.presentation.state.PinLoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinLoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PinLoginUiState())
    val uiState: StateFlow<PinLoginUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<AuthEvent?>(null)
    val events: StateFlow<AuthEvent?> = _events.asStateFlow()

    fun appendPin(digit: String) {
        val currentPin = _uiState.value.pin
        if (currentPin.length < 6) {
            _uiState.value = _uiState.value.copy(
                pin = currentPin + digit,
                error = null
            )
        }
    }

    fun deletePin() {
        val currentPin = _uiState.value.pin
        if (currentPin.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                pin = currentPin.dropLast(1),
                error = null
            )
        }
    }

    fun clearPin() {
        _uiState.value = _uiState.value.copy(pin = "", error = null)
    }

    fun loginWithPin() {
        val currentState = _uiState.value
        val pin = currentState.pin

        if (pin.isEmpty()) {
            _uiState.value = currentState.copy(error = "Enter PIN")
            return
        }

        if (pin.length < 4) {
            _uiState.value = currentState.copy(error = "PIN must be at least 4 digits")
            return
        }

        _uiState.value = currentState.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val result = authRepository.loginWithPin(pin)
                result.onSuccess { session ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        loginSuccess = true,
                        pin = ""
                    )
                    _events.value = AuthEvent.LoginSuccess(session.userId)
                }.onFailure { error ->
                    val newAttempts = currentState.attemptCount + 1
                    val attemptsLeft = currentState.maxAttempts - newAttempts
                    
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        error = error.message ?: "Invalid PIN",
                        attemptCount = newAttempts,
                        pin = ""
                    )
                    
                    if (attemptsLeft <= 0) {
                        _events.value = AuthEvent.PinLoginError(
                            "Too many failed attempts. Please use email login.",
                            0
                        )
                    } else {
                        _events.value = AuthEvent.PinLoginError(
                            error.message ?: "Invalid PIN",
                            attemptsLeft
                        )
                    }
                }
            } catch (e: Exception) {
                val newAttempts = currentState.attemptCount + 1
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred",
                    attemptCount = newAttempts,
                    pin = ""
                )
                _events.value = AuthEvent.PinLoginError(
                    e.message ?: "An error occurred",
                    currentState.maxAttempts - newAttempts
                )
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}
