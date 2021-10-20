package com.social.data.paging

import android.accounts.NetworkErrorException
import android.util.Log
import com.social.data.models.FeedModel
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.social.data.clients.api.RetrofitApiClient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class FeedPagingDataSource(private val apiClient: RetrofitApiClient, var userId: Long = 0) : PagingSource<Int, FeedModel>() {
    override fun getRefreshKey(state: PagingState<Int, FeedModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedModel> {
        try {
            var nextPageNumber = params.key ?: 1
            val response = apiClient.getFeed(userId, nextPageNumber).first()

            return LoadResult.Page(
                    data = response,
                    prevKey = null,
                    nextKey = ++nextPageNumber)
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            e.printStackTrace()
        }
        return LoadResult.Error(throwable = NetworkErrorException())
    }
}