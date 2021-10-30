package com.social.presentation.preferences

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.social.R
import com.social.databinding.FragmentPreferencesBinding
import com.social.presentation.home.activities.ActivityStarter
import com.social.presentation.base.BaseFragment
import com.social.presentation.auth.AuthViewModel
import com.social.presentation.auth.FacebookAuthViewModel
import com.social.presentation.auth.FirebaseAuthViewModel
import com.social.presentation.auth.GoogleAuthViewModel
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentPreferences : BaseFragment<FragmentPreferencesBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPreferencesBinding
        get() = FragmentPreferencesBinding::inflate

    private val firebaseAuthViewModel: FirebaseAuthViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val googleAuthViewModel: GoogleAuthViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val facebookAuthViewModel: FacebookAuthViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun setup() {

        with(binding) {
            btnOptionsChangeEmail.setOnClickListener(this@FragmentPreferences)
            btnOptionsChangePassword.setOnClickListener(this@FragmentPreferences)
            btnOptionsHelp.setOnClickListener(this@FragmentPreferences)
            btnOptionsLogout.setOnClickListener(this@FragmentPreferences)
        }

    }

    override fun collectViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    firebaseAuthViewModel.uiState.collect {
                        handleState(it)
                    }
                }
                launch {
                    googleAuthViewModel.uiState.collect {
                        handleState(it)
                    }
                }
                launch {
                    facebookAuthViewModel.uiState.collect {
                        handleState(it)
                    }
                }
            }
        }
    }

    private fun handleState(uiState: AuthViewModel.UiState){

        when(uiState){
            is AuthViewModel.Success.SignOut -> {
                val iFirstActivity = Intent(requireContext(), ActivityStarter::class.java)
                iFirstActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(iFirstActivity)
            }
            else -> {

            }
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_options_change_email -> {

            }
            R.id.btn_options_change_password -> {

            }
            R.id.btn_options_help -> {

            }
            R.id.btn_options_suggestions -> {

            }
            R.id.btn_options_logout -> {
                googleAuthViewModel.signOut()
                firebaseAuthViewModel.signOut()
                facebookAuthViewModel.signOut()
            }
        }
    }
}