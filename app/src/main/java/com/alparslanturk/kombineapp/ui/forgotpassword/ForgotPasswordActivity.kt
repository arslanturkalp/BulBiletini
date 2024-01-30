package com.alparslanturk.kombineapp.ui.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityForgotPasswordBinding
import com.alparslanturk.kombineapp.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.login.LoginActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordActivity: BaseActivity() {

    private val binding by lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupObservers()
        binding.apply {
            btnChangePassword.setOnClickListener {
                viewModel.changePassword(ForgotPasswordRequest(
                    username = edtUserName.text.toString(),
                    email = edtMail.text.toString(),
                    verificationCode = edtVerificationCode.text.toString(),
                    newPassword = edtPassword.text.toString()
                ))
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                forgotPasswordFlow.collect {
                    when (it) {
                        is Result.Error -> {
                            showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {
                            if (it.code == 300) {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            } else {
                                navigateToLogin()
                            }
                        }
                        is Result.Auth -> {}
                    }
                }
            }
        }
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(this@ForgotPasswordActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }
}