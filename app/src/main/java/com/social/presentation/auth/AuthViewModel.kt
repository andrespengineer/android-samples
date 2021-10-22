package com.social.presentation.auth

import com.social.data.models.ProfileModel
import com.social.data.local.PreferencesDataSource
import com.social.data.clients.auth.Authenticator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class AuthViewModel constructor(private val authenticator: Authenticator, private val preferencesDataSource: PreferencesDataSource) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> = _uiState

    fun signUp(email: String, password: String){

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            authenticator.signUp(email, password).catch {
                _uiState.value = Failed.RequestError
            }.collectLatest {
                _uiState.value = Success.SignIn(user = it)
            }
            _uiState.value = UiState.Complete
        }
    }

    fun signIn(email: String, password: String){

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            authenticator.signInWithEmail(email, password).catch {
                _uiState.value = Failed.RequestError
            }.collect {
                _uiState.value = Success.SignIn(user = it)
            }
        }

    }

    fun signOut() {
        authenticator.signOut()
        preferencesDataSource.clearData(ProfileModel.PROFILE, ProfileModel.PROFILE)
        _uiState.value = UiState.Complete
    }

    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class Success : UiState() {
        data class SignIn(val user: ProfileModel) : Success()
    }

    sealed class Failed : UiState() {
        object RequestError : Failed()
    }

    sealed class Validation : UiState() {
        object ValidationFailed : Validation()
    }


}