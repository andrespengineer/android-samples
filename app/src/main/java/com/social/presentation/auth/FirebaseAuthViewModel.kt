package com.social.presentation.auth

import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.FirebaseAuth
import com.social.presentation.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel @Inject constructor(authenticator: FirebaseAuth, preferencesDataSource: PreferencesDataSource) : AuthViewModel(authenticator, preferencesDataSource)