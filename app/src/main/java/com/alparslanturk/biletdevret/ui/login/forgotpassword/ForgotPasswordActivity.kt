package com.alparslanturk.biletdevret.ui.login.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getMail
import com.alparslanturk.biletdevret.application.SessionManager.getUserName
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.databinding.ActivityForgotPasswordBinding
import com.alparslanturk.biletdevret.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.login.LoginActivity
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity() {

    private val binding by lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()
        binding.apply {
            btnChangePassword.setOnClickListener {
                viewModel.changePassword(
                    ForgotPasswordRequest(
                        username = getUserName(),
                        email = getMail(),
                        verificationCode = edtVerificationCode.text.toString(),
                        newPassword = edtPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.change_password))
            setBackButton { navigateToLogin() }
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
                                showAlertDialogTheme(getString(R.string.warning), contentMessage = getString(R.string.successfully_changed_password), onPositiveButtonClick = { navigateToLogin() })
                            }
                        }
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