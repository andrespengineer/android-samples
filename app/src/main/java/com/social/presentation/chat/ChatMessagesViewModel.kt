package com.social.presentation.chat

import com.social.data.models.ChatMessageModel
import com.social.data.paging.ChatPagingDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatMessagesViewModel @Inject constructor(private val chatPagingDataSource: ChatPagingDataSource): ViewModel() {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val chatFlow = Pager(config = PagingConfig(pageSize = 10))
    {
        chatPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getMessages(userId: Long) {

        chatPagingDataSource.userId = userId

        viewModelScope.launch {
            chatFlow.catch {
                _uiState.value = UiState.Complete
                _uiState.value = Failed.RequestError
            }
            .collectLatest { data ->
                _uiState.value = UiState.Complete
                _uiState.value = Success.Messages(data = data)
            }
        }
    }



    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class Success : UiState() {
        data class Messages(val data: PagingData<ChatMessageModel>) : Success()
    }

    sealed class Failed : UiState() {
        object RequestError : Failed()
    }

}