package com.social.presentation.auth

import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.FacebookAuth
import com.social.presentation.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacebookAuthViewModel @Inject constructor(authenticator: FacebookAuth, preferencesDataSource: PreferencesDataSource) : AuthViewModel(authenticator, preferencesDataSource)