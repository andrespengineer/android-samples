package com.social.presentation.viewmodels

import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseAuthViewModel @Inject constructor(authenticator: FirebaseAuth, preferencesDataSource: PreferencesDataSource) : AuthViewModel(authenticator, preferencesDataSource)