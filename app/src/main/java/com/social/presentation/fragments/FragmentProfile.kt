package com.social.presentation.fragments

import android.animation.ObjectAnimator
import android.os.CountDownTimer
import android.view.*
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.viewModels
import com.social.presentation.base.BaseFragment
import com.social.data.models.ProfileModel
import com.social.databinding.FragmentProfileBinding
import com.social.presentation.viewmodels.ProfileViewModel
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.social.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
                            is ProfileViewModel.StateSuccess.CachedUser -> profileViewModel.getUserProfile(
                                it.user.id
                            )
                            else -> {
                                showLoading(false)
                            }
                        }
                    }
                }
                launch {
                    profileViewModel.userState.collect {
                        when (it) {
                            is ProfileViewModel.UiState.Loading -> showLoading(true)
                            is ProfileViewModel.StateSuccess.User -> {
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

        binding.tvProfileShotsCounter.text = data.drinkCount.toString()
        binding.tvProfileFavoriteShot.text = data.favoriteDrink
        binding.tvProfileFavoriteSong.text = data.favoriteSong
        binding.tvProfileInstagram.text = data.instagram
        binding.tvProfileUsername.text = data.username

        binding.ivProfilePhoto.load(data.thumbnail){
            crossfade(true)
        }

    }

    override fun showLoading(show: Boolean) {

    }

    override fun onRefresh() {
        profileViewModel.getCachedUser(true)
    }

}