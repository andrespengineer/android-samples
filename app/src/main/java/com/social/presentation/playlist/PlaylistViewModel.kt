package com.social.presentation.playlist

import com.social.data.models.PlaylistModel
import com.social.data.paging.PlaylistPagingDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.social.data.clients.api.RetrofitApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val apiClient: RetrofitApiClient, private val playlistPagingDataSource: PlaylistPagingDataSource): ViewModel() {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val _playlistState = MutableStateFlow<UiState>(UiState.Loading)
    val playlistState: StateFlow<UiState> = _playlistState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val _lastPlaylistState = MutableStateFlow<UiState>(UiState.Loading)
    val lastPlaylistState: StateFlow<UiState> = _lastPlaylistState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val playlistFlow = Pager(config = PagingConfig(pageSize = 10))
    {
        playlistPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getPlaylist(userId: Long, query: String) {

        playlistPagingDataSource.userId = userId
        playlistPagingDataSource.query = query

        viewModelScope.launch {
            playlistFlow.catch { exception ->
                run {
                    _playlistState.value = UiState.Complete
                    _playlistState.value = Failed.RequestError(exception)
                }
            }
            .collectLatest {
                _playlistState.value = UiState.Complete
                _playlistState.value = Success.Playlist(playlist = it)
            }
        }
    }

    fun getLastPlaylist(userId: Long){
        viewModelScope.launch {
            apiClient.getLastPlaylist(userId)
                .catch { exception ->
                    run {
                        _lastPlaylistState.value = UiState.Complete
                        _lastPlaylistState.value = Failed.RequestError(exception)
                    }
                }
                .collect {
                    _lastPlaylistState.value = UiState.Complete
                    _lastPlaylistState.value = Success.LivePlaylist(livePlaylist = it)
                }
        }
    }


    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class Success : UiState() {
        data class Playlist(val playlist: PagingData<PlaylistModel>) : Success()
        data class LivePlaylist(val livePlaylist: List<PlaylistModel>) : Success()
    }

    sealed class Failed : UiState() {
        data class RequestError(val throwable: Throwable) : Failed()
    }
}