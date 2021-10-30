package com.social.presentation.profile

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

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val _cachedUserState = MutableStateFlow<UiState>(UiState.Loading)

    val cachedUserState: StateFlow<UiState> = _cachedUserState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val _userState = MutableStateFlow<UiState>(UiState.Loading)
    val userState: StateFlow<UiState> = _userState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val cachedUserFlow = flowOf(fetchCachedUser())

    fun getUserProfile(userId: Long) {
        viewModelScope.launch {
            apiClient.getProfile(userId)
                .catch { exception ->
                    _userState.value = UiState.Complete
                    _userState.value = Failed.RequestError(exception)
                }
                .collect {
                    _userState.value = UiState.Complete
                    updateCachedUser(user = it)
                    _userState.value = if (it != null) Success.User(it) else Success.UserNotFound
                }
        }
    }

    fun getCachedUser() {
        viewModelScope.launch {
            cachedUserFlow.catch { exception ->
                _cachedUserState.value = UiState.Complete
                _cachedUserState.value = Failed.RequestError(exception)
            }.collect {
                _cachedUserState.value = UiState.Complete
                _cachedUserState.value = if (it == null) {
                    Success.CachedUserNotFound
                } else Success.CachedUser(it)
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
        preferencesDataSource.saveData(ProfileModel.PROFILE, ProfileModel.PROFILE, user, object: TypeToken<ProfileModel>() {}.type)
    }

    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class Success : UiState() {
        data class User(val user: ProfileModel) : Success()
        data class CachedUser(val user: ProfileModel) : Success()
        object UserNotFound : Failed()
        object CachedUserNotFound : Failed()
    }

    sealed class Failed : UiState() {
        data class RequestError(val throwable: Throwable) : Failed()
    }
}