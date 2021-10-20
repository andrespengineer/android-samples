package com.social.hilt

import com.social.data.clients.auth.Authenticator
import com.social.data.clients.auth.FacebookAuth
import com.social.data.clients.auth.FirebaseAuth
import com.social.data.clients.auth.GoogleAuth
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class AuthModule {
    @Binds
    abstract fun provideGoogleAuth(googleAuth: GoogleAuth) : Authenticator
    @Binds
    abstract fun provideFacebookAuth(facebookAuth: FacebookAuth) : Authenticator
    @Binds
    abstract fun provideFirebaseAuth(firebaseAuth: FirebaseAuth) : Authenticator
}