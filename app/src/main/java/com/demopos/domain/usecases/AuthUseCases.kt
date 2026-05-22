package com.demopos.domain.usecases

import com.demopos.domain.entities.AuthSession
import com.demopos.domain.repositories.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthSession> {
        return authRepository.login(email, password)
    }
}

class LoginWithPinUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(pin: String): Result<AuthSession> {
        return authRepository.loginWithPin(pin)
    }
}

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String): Result<Unit> {
        return authRepository.logout(userId)
    }
}

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String): Result<AuthSession> {
        return authRepository.refreshToken(userId)
    }
}

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.getCurrentUser()
}

class RememberUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: String, rememberMe: Boolean) {
        // Implementation handled by repository
    }
}
