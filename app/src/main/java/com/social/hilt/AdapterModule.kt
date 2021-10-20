package com.social.hilt

import com.social.presentation.adapters.*
import com.social.presentation.dialogfragments.DialogFragmentReport
import com.social.presentation.dialogfragments.DialogFragmentMenu
import com.social.presentation.dialogfragments.DialogFragmentRateSong
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class, ActivityComponent::class)
@Module
class AdapterModule {

    @Provides
    fun providePlaylistAdapter(dialogFragmentRateSong: DialogFragmentRateSong) : PlaylistAdapter {
        return PlaylistAdapter(dialogFragmentRateSong = dialogFragmentRateSong)
    }

    @Provides
    fun provideSearchMenuAdapter(dialogFragmentMenu: DialogFragmentMenu) : SearchMenuAdapter {
        return SearchMenuAdapter(dialogFragmentMenu = dialogFragmentMenu)
    }

    @Provides
    fun provideSearchSongsAdapter(dialogFragmentRateSong: DialogFragmentRateSong) : SearchSongsAdapter{
        return SearchSongsAdapter(dialogFragmentRateSong = dialogFragmentRateSong)
    }

    @Provides
    fun provideFeedAdapter(customReportDialog: DialogFragmentReport) : FeedAdapter {
        return FeedAdapter(customReportDialog)
    }

    @Provides
    fun provideChatMessagesAdapter() : ChatMessagesAdapter {
        return ChatMessagesAdapter()
    }

    @Provides
    fun provideFeedAdapterGrid() : FeedAdapterGrid{
        return FeedAdapterGrid()
    }

    @Provides
    fun provideSwipeFilterPagerAdapter() : SwipeFiltersPagerAdapter {
        return SwipeFiltersPagerAdapter()
    }

}