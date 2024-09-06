package com.alparslanturk.bulbiletini.ui.login.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.Constants.ADMIN_PASS
import com.alparslanturk.bulbiletini.application.Constants.ADMIN_USER
import com.alparslanturk.bulbiletini.application.SessionManager.getMail
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.application.SessionManager.updateRefreshToken
import com.alparslanturk.bulbiletini.application.SessionManager.updateToken
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivityForgotPasswordBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.login.LoginActivity
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
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

        viewModel.signIn(LoginRequest(ADMIN_USER, ADMIN_PASS))

        binding.apply {
            edtUserName.setText(getUserName())
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

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Forgot Password Page", ForgotPasswordActivity::class.java.name)
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
                launch {
                    forgotPasswordFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                if (it.body?.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                                } else {
                                    showAlertDialogTheme(getString(R.string.warning), contentMessage = getString(R.string.successfully_changed_password), onPositiveButtonClick = { navigateToLogin() })
                                }
                            }
                        }
                    }
                }
                launch {
                    loginFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                it.body?.apply {
                                    if (code == 200) {
                                        data.apply {
                                            updateToken(token.accessToken)
                                            updateRefreshToken(token.refreshToken)
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
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