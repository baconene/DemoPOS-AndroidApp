package com.demopos.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val userDao: UserDao,
    private val authSessionDao: AuthSessionDao
) : AuthRepository {

    private val dataStore = context.dataStore
    private val currentUserIdKey = stringPreferencesKey("current_user_id")
    private val currentSessionIdKey = stringPreferencesKey("current_session_id")
    private val rememberedUserIdKey = stringPreferencesKey("remembered_user_id")

    override suspend fun login(email: String, password: String): Result<AuthSession> {
        return try {
            // Validate input
            if (email.isBlank() || password.isBlank()) {
                return Result.failure(Exception("Email and password required"))
            }

            // Check if user exists in local database
            var user = userDao.getUserByEmail(email)

            if (user == null) {
                // For Phase 2, we'll create a mock user
                // In production, this would call the API
                user = UserEntity(
                    id = IdGenerator.generate(),
                    email = email,
                    name = email.split("@")[0].replaceFirstChar { it.uppercase() },
                    pin = null,
                    role = "CASHIER",
                    isActive = true,
                    createdAt = Date(),
                    updatedAt = Date()
                )
                userDao.insertUser(user)
            }

            // Create session
            val session = AuthSessionEntity(
                id = IdGenerator.generate(),
                userId = user.id,
                token = IdGenerator.generate(),
                refreshToken = IdGenerator.generate(),
                expiresAt = Date(System.currentTimeMillis() + 86400000), // 24 hours
                createdAt = Date(),
                isActive = true
            )
            authSessionDao.insertSession(session)

            // Store current session
            dataStore.edit { preferences ->
                preferences[currentUserIdKey] = user.id
                preferences[currentSessionIdKey] = session.id
            }

            Result.success(session.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithPin(pin: String): Result<AuthSession> {
        return try {
            if (pin.isBlank() || pin.length < 4) {
                return Result.failure(Exception("PIN must be at least 4 digits"))
            }

            // Find user by PIN from local database
            val users = mutableListOf<UserEntity>()
            // Note: This is simplified; in real app, use a proper query
            
            Result.failure(Exception("PIN login not yet configured"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(userId: String): Result<Unit> {
        return try {
            authSessionDao.invalidateUserSessions(userId)
            dataStore.edit { preferences ->
                preferences.remove(currentUserIdKey)
                preferences.remove(currentSessionIdKey)
            }
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
                expiresAt = Date(System.currentTimeMillis() + 86400000),
                updatedAt = Date()
            )
            authSessionDao.updateSession(newSession)

            Result.success(newSession.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val userId = preferences[currentUserIdKey] ?: return@map null
            userDao.getUserById(userId)?.toDomain()
        }
    }

    override fun getCurrentSession(): Flow<AuthSession?> {
        return dataStore.data.map { preferences ->
            val sessionId = preferences[currentSessionIdKey] ?: return@map null
            authSessionDao.getSessionById(sessionId)?.toDomain()
        }
    }

    override suspend fun saveRememberedUser(user: User): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[rememberedUserIdKey] = user.id
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getRememberedUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val userId = preferences[rememberedUserIdKey] ?: return@map null
            userDao.getUserById(userId)?.toDomain()
        }
    }

    override suspend fun clearRememberedUser(): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(rememberedUserIdKey)
            }
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

    private fun UserEntity.toDomain() = User(
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
