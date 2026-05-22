package com.demopos.presentation.state

import com.demopos.domain.entities.User

// Auth Screen States
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordVisible: Boolean = false,
    val rememberMe: Boolean = false,
    val loginSuccess: Boolean = false
)

data class PinLoginUiState(
    val pin: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginSuccess: Boolean = false,
    val attemptCount: Int = 0,
    val maxAttempts: Int = 3
)

data class SessionUiState(
    val currentUser: User? = null,
    val isAuthenticated: Boolean = false,
    val sessionExpired: Boolean = false
)

// Auth Events
sealed class AuthEvent {
    data class LoginSuccess(val userId: String) : AuthEvent()
    data class LoginError(val message: String) : AuthEvent()
    data class LogoutSuccess : AuthEvent()
    object SessionExpired : AuthEvent()
    data class PinLoginError(val message: String, val attemptsLeft: Int) : AuthEvent()
}
