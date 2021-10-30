package com.social.presentation.playlist.fragments

import android.content.Intent
import android.view.*
import androidx.core.view.isVisible
import com.social.presentation.base.BaseFragment
import com.social.data.models.PlaylistModel
import com.social.databinding.FragmentPlaylistBinding
import com.social.presentation.dialogfragments.DialogFragmentRateSong
import com.social.presentation.profile.ProfileViewModel
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.social.R
import com.social.presentation.playlist.PlaylistViewModel
import com.social.presentation.playlist.activities.ActivitySearchSongs
import com.social.presentation.playlist.adapter.PlaylistAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentPlaylist : BaseFragment<FragmentPlaylistBinding>(), View.OnClickListener{

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPlaylistBinding
        get() = FragmentPlaylistBinding::inflate

    @Inject
    @FragmentScoped
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemAnimator: DefaultItemAnimator

    @Inject
    lateinit var playlistAdapter: PlaylistAdapter

    @Inject
    lateinit var dialogFragmentRateSong: DialogFragmentRateSong

    private val playListViewModel: PlaylistViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val profileViewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private lateinit var liveItem: PlaylistModel


    override fun setup() {

        playlistAdapter.fragmentManager = childFragmentManager

        with(binding){
            rvPlaylist.layoutManager = linearLayoutManager
            rvPlaylist.adapter = playlistAdapter
            rvPlaylist.itemAnimator = itemAnimator
            rvPlaylist.isNestedScrollingEnabled = false
            ibPlaylistSearch.setOnClickListener(this@FragmentPlaylist)
        }
    }

    override fun collectViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    playListViewModel.lastPlaylistState.collect {
                        when (it) {
                            is PlaylistViewModel.Success.LivePlaylist -> updatePlaylist(it.livePlaylist)
                            is PlaylistViewModel.UiState.Loading -> {
                                showLoading(true)
                            }
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    profileViewModel.cachedUserState.collect {
                        when (it) {
                            is ProfileViewModel.UiState.Loading -> {
                                showLoading(true)
                            }
                            is ProfileViewModel.Success.CachedUser -> playListViewModel.getLastPlaylist(
                                it.user.id
                            )
                            is ProfileViewModel.UiState.Complete -> { }
                            else -> { }
                        }
                    }
                }
            }
        }
    }

    override fun fetchData() {
        profileViewModel.getCachedUser()
    }

    override fun showLoading(show: Boolean) {
        binding.tvPlaylistLastFive.isVisible = show.not()
        binding.pbLayout.progressBar.isVisible = show
    }

    private fun updatePlaylist(liveItems: List<PlaylistModel>) {


        liveItem = liveItems.first()

        with(binding) {
            ivPlaylistSongLiveImage.load(liveItem.image)
            ivPlaylistSongLiveImage.setOnClickListener(this@FragmentPlaylist)
            dialogFragmentRateSong.setItemData(liveItem)
            tvPlaylistSongLiveArtist.text = liveItem.artist
            tvPlaylistSongLiveName.text = liveItem.name
        }

        playlistAdapter.submitList(liveItems.takeLast(liveItems.size - 1))
    }

    override fun onClick(v: View) {

        with(binding){
            when(v.id){
                ibPlaylistSearch.id -> startActivity(Intent(requireContext(), ActivitySearchSongs::class.java))
                ivPlaylistSongLiveImage.id -> dialogFragmentRateSong.show(childFragmentManager, DialogFragmentRateSong::class.simpleName)
            }
        }


    }

    override fun onRefresh() {
        fetchData()
    }


}