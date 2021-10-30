package com.social.presentation.auth

import com.social.data.models.ProfileModel
import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.Authenticator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.social.presentation.chat.ChatMessagesViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class AuthViewModel constructor(private val authenticator: Authenticator, private val preferencesDataSource: PreferencesDataSource) : ViewModel() {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    val uiState : StateFlow<UiState> = _uiState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    fun signUp(email: String, password: String){

        viewModelScope.launch {
            authenticator.signUp(email, password).catch {
                _uiState.value = Failed.RequestError
            }.collectLatest {
                _uiState.value = UiState.Complete
                _uiState.value = Success.SignIn(user = it)
            }
        }
    }

    fun signIn(email: String, password: String){

        viewModelScope.launch {
            authenticator.signInWithEmail(email, password).catch {
                _uiState.value = UiState.Complete
                _uiState.value = Failed.RequestError
            }.collectLatest {
                _uiState.value = UiState.Complete
                _uiState.value = Success.SignIn(user = it)
            }
        }

    }

    fun signOut() {
        authenticator.signOut()
        preferencesDataSource.clearData(ProfileModel.PROFILE, ProfileModel.PROFILE)
        _uiState.value = Success.SignOut
    }

    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class Success : UiState() {
        data class SignIn(val user: ProfileModel) : Success()
        object SignOut : Success()
    }

    sealed class Failed : UiState() {
        object RequestError : Failed()
    }

    sealed class Validation : UiState() {
        object ValidationFailed : Validation()
    }


}