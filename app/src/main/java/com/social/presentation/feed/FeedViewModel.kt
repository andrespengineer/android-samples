package com.social.presentation.feed

import com.social.data.models.AdvertiseModel
import com.social.data.models.FeedModel
import com.social.data.paging.FeedPagingDataSource
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
class FeedViewModel @Inject constructor(private val apiClient: RetrofitApiClient, private val feedPagingDataSource: FeedPagingDataSource): ViewModel() {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val _feedState = MutableStateFlow<UiState>(UiState.Loading)
    val feedState: StateFlow<UiState> = _feedState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val _advState = MutableStateFlow<UiState>(UiState.Loading)
    val advState: StateFlow<UiState> = _advState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val feedFlow = Pager(config = PagingConfig(pageSize = 10)) {
        feedPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getAds(){
        viewModelScope.launch {
            apiClient.getAdvertisements()
                .catch { exception -> _advState.value =
                    Failed.RequestError(exception)
                }
                .collect { _advState.value = Success.Ads(advertisements = it) }
        }
    }

    fun getFeed(userId: Long) {

        feedPagingDataSource.userId = userId

        viewModelScope.launch {
            launch {
                feedFlow.catch { exception -> _feedState.value =
                    Failed.RequestError(exception)
                }.collectLatest { _feedState.value = Success.Feed(feedData = it) }
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
    }

    sealed class Success : UiState() {
        data class Feed(val feedData: PagingData<FeedModel>) : Success()
        data class Ads(val advertisements: List<AdvertiseModel>) : Success()

    }

    sealed class Failed : UiState() {
        data class RequestError(val throwable: Throwable) : Failed()
    }

}