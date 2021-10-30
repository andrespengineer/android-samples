package com.social.hilt

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.social.R
import com.social.presentation.feed.adapter.FeedAdapterGrid
import com.social.presentation.controls.SpacesItemDecoration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Named

@InstallIn(ActivityComponent::class, FragmentComponent::class)
@Module
class RecyclerViewModule {

    companion object {
        const val LAYOUT_MANAGER_VERTICAL_REVERSED = "LAYOUT_MANAGER_VERTICAL_REVERSED"
        const val SPAN_COUNT = "SPAN_COUNT"
        const val SPACING = "SPACING"
    }

    @Provides
    @Named(LAYOUT_MANAGER_VERTICAL_REVERSED)
    fun provideLinearLayoutManagerVRForFragment(@ActivityContext context: Context) : LinearLayoutManager {
        return LinearLayoutManager(context, RecyclerView.VERTICAL, true)
    }

    @Provides
    fun provideLinearLayoutManagerForFragment(@ActivityContext context: Context) : LinearLayoutManager {
        return LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    @Provides
    @Named(SPACING)
    fun provideGridViewSpacing(@ActivityContext context: Context) : Int {
        return context.resources.getDimensionPixelSize(R.dimen.grid_view_spacing_timeline)
    }

    @Provides
    @Named(SPAN_COUNT)
    fun provideFixedSpanCount() : Int {
        return 3
    }

    @Provides
    fun provideItemSpaceDecoration(@Named(SPAN_COUNT) spanCount: Int, @Named(SPACING) spacing: Int) : SpacesItemDecoration {
        return SpacesItemDecoration(spanCount, spacing = spacing, includeEdge = false)
    }

    @Provides
    fun provideGridLayoutManager(@ActivityContext context: Context) : GridLayoutManager {
        return GridLayoutManager(context, FeedAdapterGrid.NUMBER_OF_COLUMNS)
    }

    @Provides
    fun provideItemAnimator() : DefaultItemAnimator {
        return DefaultItemAnimator()
    }
}