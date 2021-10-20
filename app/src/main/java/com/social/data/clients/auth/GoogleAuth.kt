package com.social.data.clients.auth

import com.social.data.models.ProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GoogleAuth @Inject constructor(): Authenticator {
    override suspend fun signInWithUsername(username: String, password: String): Flow<ProfileModel> {
        return flowOf(ProfileModel())
    }

    override suspend fun signInWithEmail(email: String, password: String): Flow<ProfileModel> {
        return flowOf(ProfileModel())
    }

    override suspend fun signUp(email: String, password: String): Flow<ProfileModel> {
        return flowOf(ProfileModel())
    }

    override fun signOut() {

    }
}