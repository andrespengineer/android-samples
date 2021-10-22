package com.social.hilt

import android.app.Activity
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
import javax.inject.Named

@InstallIn(ActivityComponent::class, FragmentComponent::class)
@Module
class RecyclerViewModule {

    companion object {
        const val LAYOUT_MANAGER_VERTICAL_REVERSED = "LAYOUT_MANAGER_VERTICAL_REVERSED"
        const val SPAN_COUNT = "SPAN_COUNT"
        const val SPACING = "SPACING"
        const val ACTIVITY_SCOPED = "ACTIVITY_SCOPED"
    }

    @Provides
    @Named(LAYOUT_MANAGER_VERTICAL_REVERSED)
    fun provideLinearLayoutManagerVRForFragment(fragment: Fragment) : LinearLayoutManager {
        return LinearLayoutManager(fragment.requireContext(), RecyclerView.VERTICAL, true)
    }

    @Provides
    fun provideLinearLayoutManagerForFragment(fragment: Fragment) : LinearLayoutManager {
        return LinearLayoutManager(fragment.requireContext(), RecyclerView.VERTICAL, false)
    }

    @Provides
    @Named(ACTIVITY_SCOPED)
    fun provideLinearLayoutManagerForActivity(activity: Activity) : LinearLayoutManager {
        return LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    @Provides
    @Named(SPACING)
    fun provideGridViewSpacing(fragment: Fragment) : Int {
        return fragment.requireContext().resources.getDimensionPixelSize(R.dimen.grid_view_spacing_timeline)
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
    fun provideGridLayoutManager(fragment: Fragment) : GridLayoutManager {
        return GridLayoutManager(fragment.requireContext(), FeedAdapterGrid.NUMBER_OF_COLUMNS)
    }

    @Provides
    fun provideItemAnimator() : DefaultItemAnimator {
        return DefaultItemAnimator()
    }
}