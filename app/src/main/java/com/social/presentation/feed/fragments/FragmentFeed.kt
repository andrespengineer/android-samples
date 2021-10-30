package com.social.presentation.feed.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.social.presentation.base.BaseFragment
import com.social.presentation.controls.SpacesItemDecoration
import com.social.databinding.FragmentFeedRecyclerViewBinding
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.social.R
import com.social.presentation.base.BaseLoadStateAdapter
import com.social.presentation.feed.FeedViewModel
import com.social.presentation.feed.adapter.FeedAdapter
import com.social.presentation.feed.adapter.FeedAdapterGrid
import com.social.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentFeed : BaseFragment<FragmentFeedRecyclerViewBinding>(), FeedAdapterGrid.ItemClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedRecyclerViewBinding
        get() = FragmentFeedRecyclerViewBinding::inflate

    @Inject
    lateinit var listAdapter: FeedAdapter

    @Inject
    lateinit var gridAdapter: FeedAdapterGrid

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemDecoration: SpacesItemDecoration

    @Inject
    lateinit var itemAnimator: DefaultItemAnimator

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    private val feedViewModel: FeedViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val profileViewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private val onBackPressedCallback = object : OnBackPressedCallback(false){
        override fun handleOnBackPressed() {
            switchFeedToDefault()
            isEnabled = isFeedModeDefault().not()
        }
    }

    override fun setup() {

        // Setup LayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) gridLayoutManager.spanCount else 1
            }
        }

        gridAdapter.itemClickListener = this
        listAdapter.fragmentManager = childFragmentManager
        binding.rvFeed.itemAnimator = itemAnimator
        binding.rvFeed.removeItemDecoration(itemDecoration)
        binding.rvFeed.addItemDecoration(itemDecoration)
        binding.rvFeed.layoutManager = gridLayoutManager
        binding.rvFeed.adapter = gridAdapter

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }


    override fun collectViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    feedViewModel.advState.collect {
                        when (it) {
                            is FeedViewModel.Success.Ads -> {
                                listAdapter.advertisementList.apply {
                                    clear()
                                    addAll(it.advertisements)
                                }
                                gridAdapter.advertisementList.apply {
                                    clear()
                                    addAll(it.advertisements)
                                }
                            }
                            is FeedViewModel.UiState.Loading -> {
                                showLoading(true)
                            }
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    feedViewModel.feedState.collect {
                        when (it) {
                            is FeedViewModel.Success.Feed -> {
                                showLoading(false)
                                launch {
                                    gridAdapter.submitData(it.feedData)
                                }
                                launch {
                                    listAdapter.submitData(it.feedData)
                                }
                            }
                            is FeedViewModel.UiState.Loading -> {
                                showLoading(true)
                            }
                            else -> { }
                        }
                    }
                }
                launch {
                    profileViewModel.cachedUserState.collect {
                        when(it) {
                            is ProfileViewModel.Success.CachedUser -> {
                                feedViewModel.getAds()
                                feedViewModel.getFeed(it.user.id)
                            }
                            else -> {

                            }
                        }
                    } }
            }
        }
    }

    override fun fetchData() {
        profileViewModel.getCachedUser()
    }

    override fun onItemClick(position: Int) {
        if(isFeedModeDefault()) {
            binding.rvFeed.layoutManager = linearLayoutManager
            binding.rvFeed.adapter = null
            binding.rvFeed.adapter = listAdapter.withLoadStateFooter(BaseLoadStateAdapter(listAdapter))
            if(position != RecyclerView.NO_POSITION)
                binding.rvFeed.scrollToPosition(position)
        }

        onBackPressedCallback.isEnabled = true
    }

    override fun showLoading(show: Boolean) {
        binding.pbLayout.progressBar.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun onRefresh() {
        fetchData()
    }

    private fun isFeedModeDefault() : Boolean {
        return binding.rvFeed.layoutManager === gridLayoutManager
    }

    private fun switchFeedToDefault() {

        onBackPressedCallback.isEnabled = false

        if (isFeedModeDefault().not()) {
            val position = linearLayoutManager.findFirstVisibleItemPosition()
            binding.rvFeed.layoutManager = gridLayoutManager
            binding.rvFeed.adapter = gridAdapter

            if(position != RecyclerView.NO_POSITION)
                binding.rvFeed.scrollToPosition(position)

            onBackPressedCallback.isEnabled = isFeedModeDefault().not()
        }
    }
}