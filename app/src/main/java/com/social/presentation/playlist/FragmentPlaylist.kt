package com.social.presentation.playlist

import android.content.Intent
import android.view.*
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
import coil.request.CachePolicy
import com.social.R
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
        binding.rvPlaylist.layoutManager = linearLayoutManager
        binding.rvPlaylist.adapter = playlistAdapter
        binding.rvPlaylist.itemAnimator = itemAnimator
        binding.rvPlaylist.isNestedScrollingEnabled = false

        binding.ibPlaylistSearch.setOnClickListener(this)


    }

    override fun collectViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    playListViewModel.lastPlaylistState.collect {
                        when (it) {
                            is PlaylistViewModel.StateSuccess.LivePlaylist -> updatePlaylist(it.livePlaylist)
                            is PlaylistViewModel.UiState.Loading -> showLoading(true)
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    profileViewModel.cachedUserState.collect {
                        when (it) {
                            is ProfileViewModel.StateSuccess.CachedUser -> playListViewModel.getLastPlaylist(
                                it.user.id
                            )
                            else -> showLoading(false)
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

    }

    private fun updatePlaylist(liveItems: List<PlaylistModel>) {

        liveItem = liveItems.first()
        binding.ivPlaylistSongLiveImage.load(liveItem.image) {
            memoryCacheKey(liveItem.image + liveItem.key)
        }

        binding.ivPlaylistSongLiveImage.setOnClickListener(this)
        dialogFragmentRateSong.setItemData(liveItem)

        binding.tvPlaylistSongLiveArtist.text = liveItem.artist
        binding.tvPlaylistSongLiveName.text = liveItem.name

        playlistAdapter.submitList(liveItems.takeLast(liveItems.size - 1))
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ibPlaylistSearch -> startActivity(Intent(requireContext(), ActivitySearchSongs::class.java))
            R.id.ivPlaylistSongLiveImage -> dialogFragmentRateSong.show(childFragmentManager, DialogFragmentRateSong::class.simpleName)
        }

    }

    override fun onRefresh() {
        profileViewModel.getCachedUser(true)
    }


}