package com.demopos.data.repositories

import com.demopos.data.local.dao.AuthSessionDao
import com.demopos.data.local.dao.UserDao
import com.demopos.data.local.entities.AuthSessionEntity
import com.demopos.data.local.entities.UserEntity
import com.demopos.domain.entities.AuthSession
import com.demopos.domain.entities.User
import com.demopos.domain.repositories.AuthRepository
import com.demopos.utils.IdGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val authSessionDao: AuthSessionDao
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthSession> {
        return try {
            // Mock implementation - replace with actual API call
            val user = UserEntity(
                id = IdGenerator.generate(),
                email = email,
                name = email.split("@")[0],
                pin = null,
                role = "CASHIER",
                isActive = true,
                createdAt = Date(),
                updatedAt = Date()
            )
            userDao.insertUser(user)
            
            val session = AuthSessionEntity(
                id = IdGenerator.generate(),
                userId = user.id,
                token = IdGenerator.generate(),
                refreshToken = IdGenerator.generate(),
                expiresAt = Date(System.currentTimeMillis() + 86400000),
                createdAt = Date(),
                isActive = true
            )
            authSessionDao.insertSession(session)
            
            Result.success(session.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithPin(pin: String): Result<AuthSession> {
        return try {
            // Mock implementation
            Result.failure(Exception("PIN login not yet implemented"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(userId: String): Result<Unit> {
        return try {
            authSessionDao.invalidateUserSessions(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun refreshToken(userId: String): Result<AuthSession> {
        return try {
            val currentSession = authSessionDao.getActiveSessionForUser(userId)
                ?: return Result.failure(Exception("No active session"))
            
            val newSession = currentSession.copy(
                token = IdGenerator.generate(),
                updatedAt = Date()
            )
            authSessionDao.updateSession(newSession)
            
            Result.success(newSession.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        // To be implemented with actual session storage
        return kotlinx.coroutines.flow.flowOf(null)
    }

    override fun getCurrentSession(): Flow<AuthSession?> {
        // To be implemented with actual session storage
        return kotlinx.coroutines.flow.flowOf(null)
    }

    override suspend fun saveRememberedUser(user: User): Result<Unit> {
        return try {
            val userEntity = user.toEntity()
            userDao.insertUser(userEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getRememberedUser(): Flow<User?> {
        // To be implemented with DataStore
        return kotlinx.coroutines.flow.flowOf(null)
    }

    override suspend fun clearRememberedUser(): Result<Unit> {
        return try {
            // To be implemented
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun AuthSessionEntity.toDomain() = AuthSession(
        id = id,
        userId = userId,
        token = token,
        refreshToken = refreshToken,
        expiresAt = expiresAt,
        createdAt = createdAt,
        isActive = isActive
    )

    private fun User.toEntity() = UserEntity(
        id = id,
        email = email,
        name = name,
        pin = pin,
        role = role,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
