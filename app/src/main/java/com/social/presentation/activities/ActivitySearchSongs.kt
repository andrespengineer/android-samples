package com.social.presentation.activities

import android.view.LayoutInflater
import android.view.View
import com.social.R
import com.social.data.models.ProfileModel
import com.social.databinding.ActivitySearchSongsBinding
import com.social.presentation.adapters.SearchSongsAdapter
import com.social.presentation.base.BaseActivity
import com.social.presentation.dialogfragments.DialogFragmentSuggestSong
import com.social.presentation.viewmodels.PlaylistViewModel
import com.social.presentation.viewmodels.ProfileViewModel
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.social.hilt.RecyclerViewModule.Companion.ACTIVITY_SCOPED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class ActivitySearchSongs : BaseActivity<ActivitySearchSongsBinding>(), SearchView.OnQueryTextListener,  View.OnClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivitySearchSongsBinding
        get() = ActivitySearchSongsBinding::inflate

    @Inject
    lateinit var searchSongsAdapter: SearchSongsAdapter

    @Inject
    lateinit var dialogFragmentSuggestSong: DialogFragmentSuggestSong

    @Inject
    @Named(ACTIVITY_SCOPED)
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var itemAnimator: DefaultItemAnimator

    private val playlistViewModel: PlaylistViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var user: ProfileModel

    override fun setup() {

        binding.ibActionBack.setOnClickListener {
            finish()
        }

        searchSongsAdapter.fragmentManager = supportFragmentManager
        binding.rvPlaylist.layoutManager = linearLayoutManager
        binding.rvPlaylist.itemAnimator = itemAnimator
        binding.rvPlaylist.adapter = searchSongsAdapter
        binding.etSearchSongs.setOnQueryTextListener(this)
        binding.btnSearchSongSuggest.setOnClickListener(this)
    }

    override fun collectViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    playlistViewModel.playlistState.collect {
                        when (it) {
                            is PlaylistViewModel.UiState.Loading -> {
                                showLoading(true)
                                showNoResults(false)
                            }
                            is PlaylistViewModel.StateSuccess.Playlist -> {
                                searchSongsAdapter.submitData(it.playlist)
                                showNoResults(false)
                            }
                            else -> {
                                showNoResults(true)
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    profileViewModel.cachedUserState.collect {
                        when (it) {
                            is ProfileViewModel.StateSuccess.CachedUser -> {
                                user = it.user
                                onQueryTextChange("")
                            }
                            else -> showNoResults(true)
                        }
                    }
                }
            }
        }
    }

    override fun fetchData() {
        profileViewModel.getCachedUser()
    }

    private fun showNoResults(show: Boolean){
        binding.btnSearchSongSuggest.visibility = if(show) View.VISIBLE else View.GONE
        binding.tvSearchSongsNoResult.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btnSearchSongSuggest -> {
                dialogFragmentSuggestSong.show(supportFragmentManager, DialogFragmentSuggestSong::class.simpleName)
            }
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        playlistViewModel.getPlaylist(user.id, query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        playlistViewModel.getPlaylist(user.id, newText)
        return true
    }

    override fun onRefresh() {
        profileViewModel.getCachedUser(true)
    }
}