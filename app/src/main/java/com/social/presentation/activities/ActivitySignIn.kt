package com.social.presentation.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.social.R
import com.social.databinding.ActivitySignInBinding
import com.social.presentation.base.BaseActivity
import com.social.presentation.viewmodels.*
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivitySignIn : BaseActivity<ActivitySignInBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivitySignInBinding
        get() = ActivitySignInBinding::inflate

    private val firebaseAuthViewModel: FirebaseAuthViewModel by viewModels()
    private val facebookAuthViewModel: FacebookAuthViewModel by viewModels()
    private val googleAuthViewModel: GoogleAuthViewModel by viewModels()

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun setup() {

        binding.ivSignInFacebook.setOnClickListener(this)
        binding.ivSignInGoogle.setOnClickListener(this)
        binding.tvSignInForgotPassword.setOnClickListener(this)
        binding.btnSignIn.setOnClickListener(this)
    }

    override fun collectViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    firebaseAuthViewModel.uiState.collect {
                        checkViewModelState(uiState = it)
                    }
                }
                launch {
                    googleAuthViewModel.uiState.collectLatest {
                        checkViewModelState(uiState = it)
                    }
                }
                launch {
                    firebaseAuthViewModel.uiState.collectLatest {
                        checkViewModelState(uiState = it)
                    }
                }
            }
        }
    }

    private fun checkViewModelState(uiState: AuthViewModel.UiState){
        when(uiState){
            is AuthViewModel.Success.SignIn -> {
                profileViewModel.saveCachedUser(user = uiState.user)
                val iHome = Intent(this@ActivitySignIn, ActivityHome::class.java)
                iHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                this@ActivitySignIn.startActivity(iHome)
                this@ActivitySignIn.finish()
            }
            is AuthViewModel.Validation.ValidationFailed -> {

            }
            else -> {

            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnSignIn -> {
                firebaseAuthViewModel.signIn(binding.etSignInUsername.text.toString(), binding.etSignInPassword.text.toString())
            }
            R.id.ivSignInFacebook -> {
                facebookAuthViewModel.signIn(binding.etSignInUsername.text.toString(), binding.etSignInPassword.text.toString())
            }
            R.id.ivSignInGoogle -> {
                googleAuthViewModel.signIn(binding.etSignInUsername.text.toString(), binding.etSignInPassword.text.toString())
            }
            R.id.tvSignInForgotPassword -> {

            }
        }
    }
}