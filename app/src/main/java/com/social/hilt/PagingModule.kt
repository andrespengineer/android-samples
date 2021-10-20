package com.social.hilt

import com.social.data.clients.api.RetrofitApiClient
import com.social.data.paging.ChatPagingDataSource
import com.social.data.paging.FeedPagingDataSource
import com.social.data.paging.MenuPagingDataSource
import com.social.data.paging.PlaylistPagingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class PagingModule {

    @Provides
    fun providePlaylistPagingDataSource(apiClient: RetrofitApiClient) : PlaylistPagingDataSource {
       return PlaylistPagingDataSource(apiClient = apiClient)
    }

    @Provides
    fun provideFeedPagingDataSource(apiClient: RetrofitApiClient) : FeedPagingDataSource {
        return FeedPagingDataSource(apiClient = apiClient)
    }

    @Provides
    fun provideChatPagingDataSource(apiClient: RetrofitApiClient) : ChatPagingDataSource{
        return ChatPagingDataSource(apiClient = apiClient)
    }

    @Provides
    fun provideMenuPagingDataSource(apiClient: RetrofitApiClient) : MenuPagingDataSource {
        return MenuPagingDataSource(apiClient = apiClient)
    }
}