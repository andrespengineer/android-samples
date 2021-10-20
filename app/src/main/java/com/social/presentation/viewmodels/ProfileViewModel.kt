package com.social.presentation.viewmodels

import com.social.data.models.ProfileModel
import com.social.data.local.PreferencesDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.social.data.clients.api.RetrofitApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val apiClient: RetrofitApiClient, private val preferencesDataSource: PreferencesDataSource): ViewModel() {

    private val _cachedUserState = MutableStateFlow<UiState>(UiState.Loading)
    val cachedUserState: StateFlow<UiState> = _cachedUserState

    private val _userState = MutableStateFlow<UiState>(UiState.Loading)
    val userState: StateFlow<UiState> = _userState

    fun getUserProfile(userId: Long) {
        _userState.value = UiState.Loading
        viewModelScope.launch {
            apiClient.getProfile(userId)
                .catch { _userState.value = StateFailed.RequestError }
                .collect {
                    updateCachedUser(user = it)
                    _userState.value = if (it != null) StateSuccess.User(it) else StateFailed.RequestError
                }
        }
    }

    fun getCachedUser(refresh: Boolean = false) {
        if(refresh)
            _cachedUserState.value = UiState.Loading

        viewModelScope.launch {
            flowOf(
                fetchCachedUser()
            ).collect {
                _cachedUserState.value = if (it == null) {
                    StateFailed.CachedUserNotFound
                } else StateSuccess.CachedUser(it)
            }
        }
    }

    private fun fetchCachedUser() : ProfileModel? {
        return preferencesDataSource.getData<ProfileModel>(
            ProfileModel.PROFILE,
            ProfileModel.PROFILE,
            object : TypeToken<ProfileModel>() {}.type
        )
    }

    private fun updateCachedUser(user: ProfileModel?){

        if(fetchCachedUser()?.id == user?.id ?: return)
            saveCachedUser(user)
    }

    fun saveCachedUser(user: ProfileModel){
        _cachedUserState.value = UiState.Loading
        preferencesDataSource.saveData(ProfileModel.PROFILE, ProfileModel.PROFILE, user, object: TypeToken<ProfileModel>() {}.type)
    }

    sealed class UiState {
        object Loading : UiState()
    }

    sealed class StateSuccess : UiState() {
        data class User(val user: ProfileModel) : StateSuccess()
        data class CachedUser(val user: ProfileModel) : StateSuccess()
    }

    sealed class StateFailed : UiState() {
        object RequestError : StateFailed()
        object CachedUserNotFound : StateFailed()
    }
}