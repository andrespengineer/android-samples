package com.social.data.paging

import android.accounts.NetworkErrorException
import com.social.data.models.ChatMessageModel
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.social.data.clients.api.RetrofitApiClient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

class ChatPagingDataSource(private val apiClient: RetrofitApiClient, var userId: Long = 0L) : PagingSource<Int, ChatMessageModel>() {

    // This is for test purpose only
    companion object {
        private const val MAX_PAGE = 3
    }

    override fun getRefreshKey(state: PagingState<Int, ChatMessageModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatMessageModel> {
        try {
            var nextPageNumber = params.key ?: 1
            val response = apiClient.getChatMessages(userId, nextPageNumber).first()
            return LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if(nextPageNumber < MAX_PAGE) ++nextPageNumber else null)
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
        }
        return LoadResult.Error(throwable = NetworkErrorException())
    }
}