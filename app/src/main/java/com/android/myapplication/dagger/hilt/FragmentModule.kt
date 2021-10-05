package com.android.myapplication.dagger.hilt

import com.android.myapplication.ui.posts.adapter.PostsAdapter
import com.android.myapplication.ui.stories.adapter.StoriesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    fun provideStoriesAdapter(): StoriesAdapter {
        return StoriesAdapter()
    }

    @Provides
    fun providePostAdapter(): PostsAdapter {
        return PostsAdapter()
    }
}