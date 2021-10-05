package com.android.myapplication.dagger.hilt

import com.android.myapplication.data.network.RetrofitApiClient
import com.android.myapplication.ui.posts.contract.FragmentPostsContract
import com.android.myapplication.ui.stories.contracts.FragmentStoriesContract
import com.android.myapplication.ui.posts.presenters.FragmentPostsPresenter
import com.android.myapplication.ui.stories.presenters.FragmentStoriesPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FragmentPresenterModule {

    @Provides
    fun provideFragmentPostPresenter(apiClient: RetrofitApiClient): FragmentPostsContract.Presenter {
        return FragmentPostsPresenter(apiClient)
    }

    @Provides
    fun provideFragmentStoriesPresenter(apiClient: RetrofitApiClient): FragmentStoriesContract.Presenter {
        return FragmentStoriesPresenter(apiClient)
    }
}