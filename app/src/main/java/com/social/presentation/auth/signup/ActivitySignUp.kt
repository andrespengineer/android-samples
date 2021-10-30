package com.social.presentation.auth.signup

import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.social.R
import com.social.databinding.ActivitySignUpBinding
import com.social.presentation.base.BaseActivity
import com.social.utils.DialogUtil
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.internal.TextWatcherAdapter
import com.social.presentation.auth.AuthViewModel
import com.social.presentation.auth.FacebookAuthViewModel
import com.social.presentation.auth.FirebaseAuthViewModel
import com.social.presentation.auth.GoogleAuthViewModel
import com.social.presentation.home.activities.ActivityHome
import com.social.presentation.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class ActivitySignUp : BaseActivity<ActivitySignUpBinding>(), View.OnClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivitySignUpBinding
        get() = ActivitySignUpBinding::inflate

    private val firebaseAuthViewModel: FirebaseAuthViewModel by viewModels()
    private val facebookAuthViewModel: FacebookAuthViewModel by viewModels()
    private val googleAuthViewModel: GoogleAuthViewModel by viewModels()

    private val profileViewModel: ProfileViewModel by viewModels()

    private var watcher: TextWatcher = object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable) {
            validateFields()
        }
    }

    override fun setup() {

        binding.etSignUpUsername.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES.or(InputType.TYPE_TEXT_VARIATION_FILTER))
        binding.etSignUpEmail.setRawInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS.or(InputType.TYPE_TEXT_VARIATION_FILTER))
        binding.etSignUpPassword.setRawInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD.or(InputType.TYPE_TEXT_VARIATION_FILTER))
        binding.etSignUpUsername.addTextChangedListener(watcher)
        binding.etSignUpEmail.addTextChangedListener(watcher)
        binding.etSignUpPassword.addTextChangedListener(watcher)
        binding.btnSignUp.setOnClickListener(this)
        binding.ivSignUpFacebook.setOnClickListener(this)
        binding.ivSignUpGoogle.setOnClickListener(this)
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
                    googleAuthViewModel.uiState.collect {
                        checkViewModelState(uiState = it)
                    }
                }
                launch {
                    facebookAuthViewModel.uiState.collect {
                        checkViewModelState(uiState = it)
                    }
                }
            }
        }
    }

    private fun checkViewModelState(uiState: AuthViewModel.UiState) {
        when(uiState){
            is AuthViewModel.Success.SignIn -> {
                profileViewModel.saveCachedUser(user = uiState.user)
                val iHome = Intent(this@ActivitySignUp, ActivityHome::class.java)
                iHome.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                this@ActivitySignUp.startActivity(iHome)
                this@ActivitySignUp.finish()
            }
            is AuthViewModel.Validation.ValidationFailed -> {

            }
            else -> {

            }
        }
    }

    private fun validateFields() {
        val emailMatcher: Matcher = EMAIL_PATTERN.matcher(binding.etSignUpEmail.text.toString())
        val nameMatcher: Matcher = NAME_PATTER.matcher(binding.etSignUpUsername.text.toString().trim { it <= ' ' })
        val passwordMatcher: Matcher = PASSWORD_PATTERN.matcher(binding.etSignUpPassword.text.toString())

        with(binding) {
            tilSignUpUsername.isErrorEnabled = !(etSignUpUsername.text.toString().trim { it <= ' ' }.length > 4 && nameMatcher.find())
            tilSignUpEmail.isErrorEnabled = !(emailMatcher.find())
            tilSignUpPassword.isErrorEnabled = !(passwordMatcher.find())

            val notValid: Boolean = tilSignUpEmail.isErrorEnabled || tilSignUpPassword.isErrorEnabled || tilSignUpUsername.isErrorEnabled
            btnSignUp.isEnabled = notValid.not()
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnSignUp -> {
                firebaseAuthViewModel.signUp(binding.etSignUpEmail.text.toString(), binding.etSignUpPassword.text.toString())
            }
            R.id.ivSignUpFacebook -> {
                facebookAuthViewModel.signUp(binding.etSignUpEmail.text.toString(), binding.etSignUpPassword.text.toString())
            }
            R.id.ivSignUpGoogle -> {
                googleAuthViewModel.signUp(binding.etSignUpEmail.text.toString(), binding.etSignUpPassword.text.toString())
            }
        }
    }

    override fun onBackPressed() {
        DialogUtil.createCustomDialog(this, R.layout.custom_alert_dialog, resources.getString(R.string.back_message), onClickYes = { super.onBackPressed() })
    }

    companion object {
        private val EMAIL_PATTERN: Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
        private val NAME_PATTER: Pattern = Pattern.compile("[a-zA-Z ]*", Pattern.CASE_INSENSITIVE)
        private val PASSWORD_PATTERN: Pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    }

}