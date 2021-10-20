package com.social.presentation.viewmodels

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

    private val _feedState = MutableStateFlow<UiState>(UiState.Loading)
    val feedState: StateFlow<UiState> = _feedState

    private val _advState = MutableStateFlow<UiState>(UiState.Loading)
    val advState: StateFlow<UiState> = _advState

    private val feedFlow = Pager(config = PagingConfig(pageSize = 10)) {
        feedPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getFeed(userId: Long) {

        feedPagingDataSource.userId = userId

        viewModelScope.launch {
            launch {
                apiClient.getAdvertisements()
                        .catch { exception -> _advState.value = StateFailed.RequestError(exception) }
                        .collect { _advState.value = StateSuccess.Ads(advertisements = it) }
            }
            launch {
                feedFlow.catch { exception -> _feedState.value = StateFailed.RequestError(exception) }
                        .collectLatest { _feedState.value = StateSuccess.Feed(feedData = it) }
            }
        }
    }


    sealed class UiState {
        object Loading : UiState()
    }

    sealed class StateSuccess : UiState() {
        data class Feed(val feedData: PagingData<FeedModel>) : StateSuccess()
        data class Ads(val advertisements: List<AdvertiseModel>) : StateSuccess()

    }

    sealed class StateFailed : UiState() {
        data class RequestError(val throwable: Throwable) : StateFailed()
    }

}