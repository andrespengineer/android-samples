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

    private val _playlistState = MutableStateFlow<UiState>(UiState.Loading)
    val playlistState: StateFlow<UiState> = _playlistState

    private val _lastPlaylistState = MutableStateFlow<UiState>(UiState.Loading)
    val lastPlaylistState: StateFlow<UiState> = _lastPlaylistState

    private val playlistFlow = Pager(config = PagingConfig(pageSize = 10))
    {
        playlistPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getPlaylist(userId: Long, query: String) {

        playlistPagingDataSource.userId = userId
        playlistPagingDataSource.query = query

        viewModelScope.launch {
            playlistFlow.catch { exception -> _playlistState.value = StateFailed.RequestError(exception) }
                .collectLatest { _playlistState.value = StateSuccess.Playlist(playlist = it) }
        }
    }

    fun getLastPlaylist(userId: Long){
        viewModelScope.launch {
            apiClient.getLastPlaylist(userId)
                .catch { exception -> _lastPlaylistState.value =
                    StateFailed.RequestError(exception)
                }
                .collect { _lastPlaylistState.value = StateSuccess.LivePlaylist(livePlaylist = it) }
        }
    }


    sealed class UiState {
        object Loading : UiState()
    }

    sealed class StateSuccess : UiState() {
        data class Playlist(val playlist: PagingData<PlaylistModel>) : StateSuccess()
        data class LivePlaylist(val livePlaylist: List<PlaylistModel>) : StateSuccess()
    }

    sealed class StateFailed : UiState() {
        data class RequestError(val throwable: Throwable) : StateFailed()
    }
}