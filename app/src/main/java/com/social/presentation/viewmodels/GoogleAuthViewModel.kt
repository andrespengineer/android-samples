package com.social.presentation.viewmodels

import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.GoogleAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoogleAuthViewModel @Inject constructor(authenticator: GoogleAuth, preferencesDataSource: PreferencesDataSource) : AuthViewModel(authenticator, preferencesDataSource)