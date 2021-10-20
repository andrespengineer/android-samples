package com.social.presentation.viewmodels

import com.social.data.models.ChatMessageModel
import com.social.data.paging.ChatPagingDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import javax.inject.Inject

@HiltViewModel
class ChatMessagesViewModel @Inject constructor(private val chatPagingDataSource: ChatPagingDataSource): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val chatFlow = Pager(config = PagingConfig(pageSize = 10))
    {
        chatPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getMessages(userId: Long) {
        chatPagingDataSource.userId = userId
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            chatFlow.catch { _uiState.value = StateFailed.RequestError }
                .collectLatest { data ->
                    _uiState.value = StateSuccess.Messages(data = data.map {  it.apply {
                    this.message = validateFields(this.message)
                } } ) }
        }

    }

    private fun validateFields(string: String): String {
        val newString = StringBuilder()
        val strings = string.split(" ").toTypedArray()
        for (i in strings.indices) {
            if (strings[i].trim().isNotEmpty()) {
                newString.append(strings[i] + " ")
            }
        }
        return newString.toString()
    }



    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class StateSuccess : UiState() {
        data class Messages(val data: PagingData<ChatMessageModel>) : StateSuccess()
    }

    sealed class StateFailed : UiState() {
        object RequestError : StateFailed()
    }

}