package com.social.presentation.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.social.R
import com.social.databinding.ActivityStarterBinding
import com.social.presentation.base.BaseActivity
import com.social.presentation.viewmodels.ProfileViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityStarter : BaseActivity<ActivityStarterBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivityStarterBinding
        get() = ActivityStarterBinding::inflate

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun setup() {
        binding.btnStartLoginActivity.setOnClickListener(this)
        binding.btnStartSignUpActivity.setOnClickListener(this)

    }

    override fun collectViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.cachedUserState.collect {
                    when (it) {
                        is ProfileViewModel.StateSuccess.CachedUser -> {
                            val iHome = Intent(this@ActivityStarter, ActivityHome::class.java)
                            startActivity(iHome)
                            finish()
                        }
                        else -> {
                            // Do nothing
                        }
                    }
                }
            }
        }
    }

    override fun fetchData() {
        profileViewModel.getCachedUser(true)
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btnStartLoginActivity -> {
                val iLogin = Intent(this@ActivityStarter, ActivitySignIn::class.java)
                startActivity(iLogin)
            }
            R.id.btnStartSignUpActivity -> {
                val iSignUp = Intent(this@ActivityStarter, ActivitySignUp::class.java)
                startActivity(iSignUp)
            }
        }
    }
}