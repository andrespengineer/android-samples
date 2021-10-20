package com.social.presentation.viewmodels

import com.social.data.models.MenuModel
import com.social.data.paging.MenuPagingDataSource
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
class MenuViewModel @Inject constructor(private val apiClient: RetrofitApiClient, private val menuPagingDataSource: MenuPagingDataSource): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val pager = Pager(config = PagingConfig(pageSize = 25, prefetchDistance = 20))
    {
        menuPagingDataSource
    }

    fun getMenu(userId: Long, menuCategory: Int, query: String) {

        menuPagingDataSource.userId = userId
        menuPagingDataSource.category = menuCategory
        menuPagingDataSource.query = query

        _uiState.value = UiState.Loading
        viewModelScope.launch {
            pager.flow.cachedIn(viewModelScope)
                .catch { exception -> _uiState.value = Failed.RequestError(exception) }
                .collectLatest { _uiState.value = Success.Menu(menu = it) }
        }
    }


    sealed class UiState {
        object Loading : UiState()
        object Complete : UiState()
    }

    sealed class Success : UiState() {
        data class Menu(val menu: PagingData<MenuModel>) : Success()
    }

    sealed class Failed : UiState() {
        data class NetworkError(val throwable: Throwable) : Failed()
        data class RequestError(val throwable: Throwable) : Failed()
    }
}