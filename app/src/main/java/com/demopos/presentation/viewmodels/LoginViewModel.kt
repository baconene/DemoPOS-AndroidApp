package com.demopos.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demopos.domain.entities.User
import com.demopos.domain.repositories.AuthRepository
import com.demopos.presentation.state.AuthEvent
import com.demopos.presentation.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<AuthEvent?>(null)
    val events: StateFlow<AuthEvent?> = _events.asStateFlow()

    private val _rememberedUser = MutableStateFlow<User?>(null)
    val rememberedUser: StateFlow<User?> = _rememberedUser.asStateFlow()

    init {
        loadRememberedUser()
    }

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun togglePasswordVisibility() {
        _uiState.value = _uiState.value.copy(
            isPasswordVisible = !_uiState.value.isPasswordVisible
        )
    }

    fun toggleRememberMe() {
        _uiState.value = _uiState.value.copy(
            rememberMe = !_uiState.value.rememberMe
        )
    }

    fun login() {
        val currentState = _uiState.value
        
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(error = "Email is required")
            return
        }

        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(error = "Password is required")
            return
        }

        _uiState.value = currentState.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val result = authRepository.login(currentState.email, currentState.password)
                result.onSuccess { session ->
                    if (currentState.rememberMe) {
                        val user = authRepository.getCurrentUser()
                        // Remember user for next time
                    }
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        loginSuccess = true
                    )
                    _events.value = AuthEvent.LoginSuccess(session.userId)
                }.onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        error = error.message ?: "Login failed"
                    )
                    _events.value = AuthEvent.LoginError(error.message ?: "Login failed")
                }
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
                _events.value = AuthEvent.LoginError(e.message ?: "An error occurred")
            }
        }
    }

    private fun loadRememberedUser() {
        viewModelScope.launch {
            authRepository.getRememberedUser().collect { user ->
                _rememberedUser.value = user
                if (user != null) {
                    _uiState.value = _uiState.value.copy(email = user.email)
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}
