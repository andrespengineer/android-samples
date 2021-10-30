package com.social.presentation.menu

import com.social.data.models.MenuModel
import com.social.data.paging.MenuPagingDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val menuPagingDataSource: MenuPagingDataSource): ViewModel() {

    companion object {
        private const val TIMEOUT = 5000L
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    val uiState: StateFlow<UiState> = _uiState.stateIn(
        initialValue = UiState.Loading,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIMEOUT)
    )

    private val menuFlow = Pager(config = PagingConfig(pageSize = 10))
    {
        menuPagingDataSource
    }.flow.cachedIn(viewModelScope)

    fun getMenu(userId: Long, menuCategory: Int, query: String) {

        menuPagingDataSource.userId = userId
        menuPagingDataSource.category = menuCategory
        menuPagingDataSource.query = query

        viewModelScope.launch {
            menuFlow.catch { exception ->
                    _uiState.value = UiState.Complete
                    _uiState.value = Failed.RequestError(exception)
                 }
                .collectLatest {
                    _uiState.value = UiState.Complete
                    _uiState.value = Success.Menu(menu = it)
                }
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
        data class RequestError(val throwable: Throwable) : Failed()
    }
}