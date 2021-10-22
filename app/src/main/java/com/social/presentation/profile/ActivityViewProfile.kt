package com.social.presentation.profile

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import com.social.presentation.base.BaseActivity
import com.social.data.models.ProfileModel
import com.social.data.models.UserModel
import com.social.databinding.ActivityViewProfileBinding
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.request.CachePolicy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityViewProfile : BaseActivity<ActivityViewProfileBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityViewProfileBinding
        get() = ActivityViewProfileBinding::inflate

    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user: UserModel? = intent?.extras?.getParcelable(UserModel.USER)

        if(user?.id != null){
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    profileViewModel.userState.collect {
                        when(it) {
                            is ProfileViewModel.UiState.Loading -> showLoading(true)
                            is ProfileViewModel.StateSuccess.User -> {
                                updateProfileUi(it.user)
                                animateDrinkCount(it.user, it.user.drinkCount * 3)
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

    override fun setup() {
        // Not implemented
    }

    private fun updateProfileUi(data: ProfileModel) {

        binding.tvProfileShotsCounter.text = data.drinkCount.toString()
        binding.tvProfileFavoriteShot.text = data.favoriteDrink
        binding.tvProfileFavoriteSong.text = data.favoriteSong
        binding.tvProfileInstagram.text = data.instagram
        binding.ivProfilePhoto.load(data.thumbnail){
            memoryCacheKey(data.thumbnail + data.key)
        }
    }

    private fun animateDrinkCount(user: ProfileModel, maxDrinks: Int) {

        val totalProgress = (user.drinkCount / maxDrinks.toFloat() * 100).toInt()
        val animation: ObjectAnimator = ObjectAnimator.ofInt(binding.pbProfileShotsCounter, "progress", 0, totalProgress)
        val duration = 2000L
        animation.duration = duration
        animation.interpolator = DecelerateInterpolator()
        object : CountDownTimer(duration, duration / (user.drinkCount) + 1) {
            private var counter = 0
            override fun onTick(millisUntilFinished: Long) {
                counter += 1
                binding.tvCount.text = (counter.toString())
            }
            override fun onFinish() {
                binding.tvCount.text = user.drinkCount.toString()
            }
        }.start()
        animation.start()
    }

    override fun showLoading(show: Boolean) {

    }
}