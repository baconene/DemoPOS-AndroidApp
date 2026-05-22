package com.demopos.domain.repositories

import com.demopos.domain.entities.AuthSession
import com.demopos.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthSession>
    suspend fun loginWithPin(pin: String): Result<AuthSession>
    suspend fun logout(userId: String): Result<Unit>
    suspend fun refreshToken(userId: String): Result<AuthSession>
    
    fun getCurrentUser(): Flow<User?>
    fun getCurrentSession(): Flow<AuthSession?>
    
    suspend fun saveRememberedUser(user: User): Result<Unit>
    fun getRememberedUser(): Flow<User?>
    suspend fun clearRememberedUser(): Result<Unit>
}
