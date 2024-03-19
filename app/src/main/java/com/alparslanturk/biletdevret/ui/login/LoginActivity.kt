package com.alparslanturk.biletdevret.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getPassword
import com.alparslanturk.biletdevret.application.SessionManager.getUserName
import com.alparslanturk.biletdevret.application.SessionManager.updateMail
import com.alparslanturk.biletdevret.application.SessionManager.updatePassword
import com.alparslanturk.biletdevret.application.SessionManager.updateRefreshToken
import com.alparslanturk.biletdevret.application.SessionManager.updateToken
import com.alparslanturk.biletdevret.application.SessionManager.updateUserID
import com.alparslanturk.biletdevret.application.SessionManager.updateUserName
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.databinding.ActivityLoginBinding
import com.alparslanturk.biletdevret.domain.entities.requests.user.LoginRequest
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.main.MainActivity
import com.alparslanturk.biletdevret.ui.register.RegisterActivity
import com.alparslanturk.biletdevret.ui.login.verificationcode.VerificationCodeActivity
import com.alparslanturk.biletdevret.utils.addOnBackPressedListener
import com.alparslanturk.biletdevret.utils.setTextUnderLine
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupObservers()

        addOnBackPressedListener {
            finishAffinity()
        }

        if (getUserName() != "" && getPassword() != "") {
            navigateToMain()
        }

        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.signIn(LoginRequest(edtUserName.text.toString(), edtPassword.text.toString()))
            }
            btnRegister.setOnClickListener { navigateToRegister() }
            tvForgotMyPassword.apply {
                setTextUnderLine()
                setOnClickListener { navigateToGetVerificationCode() }
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
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
                                            updateUserID(id)
                                            updateUserName(username)
                                            updateMail(email)
                                            updatePassword(binding.edtPassword.text.toString())
                                            updateToken(token.accessToken)
                                            updateRefreshToken(token.refreshToken)
                                            navigateToMain()
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

    private fun navigateToRegister() = startActivity(RegisterActivity.createIntent(this@LoginActivity))

    private fun navigateToMain() = startActivity(MainActivity.createIntent(this@LoginActivity))

    private fun navigateToGetVerificationCode() = startActivity(VerificationCodeActivity.createIntent(this@LoginActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}