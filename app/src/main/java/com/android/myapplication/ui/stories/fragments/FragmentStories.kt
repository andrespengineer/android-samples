package com.android.myapplication.ui.stories.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.ui.stories.adapter.StoriesAdapter
import com.android.myapplication.base.BaseFragment
import com.android.myapplication.databinding.FragmentStoriesBinding
import com.android.myapplication.ui.stories.contracts.FragmentStoriesContract
import com.android.myapplication.data.models.Story
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentStories : BaseFragment<FragmentStoriesBinding, FragmentStoriesContract.View, FragmentStoriesContract.Presenter>(), FragmentStoriesContract.View {

    @Inject
    override lateinit var presenter: FragmentStoriesContract.Presenter

    lateinit var storiesAdapter: StoriesAdapter
        @Inject set

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoriesBinding
        get() = FragmentStoriesBinding::inflate


    override fun setup(view: View) {

        val linearLayoutManagerHorizontal = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        linearLayoutManagerHorizontal.initialPrefetchItemCount = 25
        binding.rvStories.layoutManager = linearLayoutManagerHorizontal
        binding.rvStories.setHasFixedSize(true)
        binding.rvStories.adapter = storiesAdapter
        presenter.getStories()
    }

    override fun updateData(storyList: List<Story>) {
        storiesAdapter.updateAdapterData(storyList)
    }
}