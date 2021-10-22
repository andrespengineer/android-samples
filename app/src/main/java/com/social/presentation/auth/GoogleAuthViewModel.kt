package com.social.presentation.auth

import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.GoogleAuth
import com.social.presentation.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoogleAuthViewModel @Inject constructor(authenticator: GoogleAuth, preferencesDataSource: PreferencesDataSource) : AuthViewModel(authenticator, preferencesDataSource)