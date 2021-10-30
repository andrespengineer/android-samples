package com.social.presentation.profile.fragments

import android.graphics.Color
import android.view.*
import com.social.presentation.base.BaseFragment
import com.social.data.models.ProfileModel
import com.social.databinding.FragmentProfileBinding
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.social.R
import com.social.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentProfile : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private val profileViewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun setup() {
        binding.ibProfilePreferences.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_profile_to_fragmentPreferences)
        }
    }

    override fun collectViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    profileViewModel.cachedUserState.collect {
                        when (it) {
                            is ProfileViewModel.UiState.Loading -> showLoading(true)
                            is ProfileViewModel.Success.CachedUser -> {
                                updateProfileUi(it.user)
                                profileViewModel.getUserProfile(it.user.id)
                            }
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    profileViewModel.userState.collectLatest {
                        when (it) {
                            is ProfileViewModel.UiState.Loading -> {
                                showLoading(true)
                            }
                            is ProfileViewModel.Success.User -> {
                                updateProfileUi(it.user)
                            }
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun fetchData() {
        profileViewModel.getCachedUser()
    }

    private fun updateProfileUi(data: ProfileModel) {

        with(binding) {
            tvProfileShotsCounter.text = data.drinkCount.toString()
            tvProfileFavoriteShot.text = data.favoriteDrink
            tvProfileFavoriteSong.text = data.favoriteSong
            tvProfileInstagram.text = data.instagram
            tvProfileUsername.text = data.username
            ivProfilePhoto.load(data.thumbnail)
        }

    }

    override fun showLoading(show: Boolean) {

    }

    override fun onRefresh() {
        fetchData()
    }

}