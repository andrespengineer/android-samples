package com.social.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.social.presentation.base.BaseFragment
import com.social.presentation.adapters.FeedAdapter
import com.social.presentation.adapters.FeedAdapterGrid
import com.social.presentation.controls.SpacesItemDecoration
import com.social.databinding.FragmentFeedRecyclerViewBinding
import com.social.presentation.viewmodels.FeedViewModel
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.social.R
import com.social.presentation.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
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

    @FragmentScoped
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemDecoration: SpacesItemDecoration

    @Inject
    lateinit var itemAnimator: DefaultItemAnimator

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    private val feedViewModel: FeedViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

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

    }

    override fun collectViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    feedViewModel.advState.collect {
                        when (it) {
                            is FeedViewModel.StateSuccess.Ads -> {
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

                            is FeedViewModel.StateSuccess.Feed -> {
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
                            is ProfileViewModel.StateSuccess.CachedUser -> feedViewModel.getFeed(it.user.id)
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
            binding.rvFeed.adapter = listAdapter
            if(position != RecyclerView.NO_POSITION)
                binding.rvFeed.scrollToPosition(position)
        }
    }

    override fun showLoading(show: Boolean) {
        binding.pbFeedLoading.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun onRefresh() {
        profileViewModel.getCachedUser(true)
    }

    fun isFeedModeDefault() : Boolean {
        return binding.rvFeed.layoutManager === gridLayoutManager
    }

    fun switchFeedToDefault() {
        if (isFeedModeDefault().not()) {
            val position = linearLayoutManager.findFirstVisibleItemPosition()
            binding.rvFeed.layoutManager = gridLayoutManager
            binding.rvFeed.adapter = gridAdapter

            if(position != RecyclerView.NO_POSITION)
                binding.rvFeed.scrollToPosition(position)
        }
    }
}