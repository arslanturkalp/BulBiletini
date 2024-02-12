package com.alparslanturk.kombineapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getPassword
import com.alparslanturk.kombineapp.application.SessionManager.getUserName
import com.alparslanturk.kombineapp.application.SessionManager.updatePassword
import com.alparslanturk.kombineapp.application.SessionManager.updateRefreshToken
import com.alparslanturk.kombineapp.application.SessionManager.updateToken
import com.alparslanturk.kombineapp.application.SessionManager.updateUserID
import com.alparslanturk.kombineapp.application.SessionManager.updateUserName
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityLoginBinding
import com.alparslanturk.kombineapp.domain.entities.requests.user.LoginRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.main.MainActivity
import com.alparslanturk.kombineapp.ui.register.RegisterActivity
import com.alparslanturk.kombineapp.ui.verificationcode.VerificationCodeActivity
import com.alparslanturk.kombineapp.utils.addOnBackPressedListener
import com.alparslanturk.kombineapp.utils.setTextUnderLine
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
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
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        updateUserID(id)
                                        updateUserName(username)
                                        updatePassword(binding.edtPassword.text.toString())
                                        updateToken(token.accessToken)
                                        updateRefreshToken(token.refreshToken)
                                        navigateToMain()
                                    }
                                }
                            }
                            is Result.Auth -> {}
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