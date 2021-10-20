package com.social.data.clients.auth

import com.social.data.models.ProfileModel
import kotlinx.coroutines.flow.Flow

interface Authenticator {
    suspend fun signInWithUsername(username: String, password: String) : Flow<ProfileModel>
    suspend fun signInWithEmail(email: String, password: String) : Flow<ProfileModel>
    suspend fun signUp(email: String, password: String) : Flow<ProfileModel>
    fun signOut()
}