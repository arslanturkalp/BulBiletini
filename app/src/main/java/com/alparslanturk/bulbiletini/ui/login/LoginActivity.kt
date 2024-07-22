@file:Suppress("DEPRECATION")

package com.alparslanturk.bulbiletini.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getPassword
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.application.SessionManager.updateMail
import com.alparslanturk.bulbiletini.application.SessionManager.updatePassword
import com.alparslanturk.bulbiletini.application.SessionManager.updateRefreshToken
import com.alparslanturk.bulbiletini.application.SessionManager.updateToken
import com.alparslanturk.bulbiletini.application.SessionManager.updateUserID
import com.alparslanturk.bulbiletini.application.SessionManager.updateUserName
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivityLoginBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.login.verificationcode.VerificationCodeActivity
import com.alparslanturk.bulbiletini.ui.main.MainActivity
import com.alparslanturk.bulbiletini.ui.register.RegisterActivity
import com.alparslanturk.bulbiletini.utils.addOnBackPressedListener
import com.alparslanturk.bulbiletini.utils.setTextUnderLine
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
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
            btnLogin.setOnClickListener { viewModel.signIn(LoginRequest(edtUserName.text.toString(), edtPassword.text.toString())) }
            btnRegister.setOnClickListener { navigateToRegister() }
            btnGoogle.setOnClickListener { createNewGoogleUser() }
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
                launch {
                    registerFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                it.body!!.apply {
                                    if (code == 300) {
                                        viewModel.signIn(LoginRequest(getUserName(), getPassword()))
                                    } else {
                                        viewModel.signIn(LoginRequest(getUserName(), getPassword()))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun createNewGoogleUser() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(this, gso)

        val intent = client.signInIntent
        signInResult.launch(intent)
    }

    private val signInResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val userName = account.email.orEmpty().substringBefore("@").lowercase()
            viewModel.register(
                RegisterRequest(
                    name = account.givenName.orEmpty(),
                    surname = account.familyName.orEmpty(),
                    username = userName,
                    password = "GmailUser",
                    email = account.email.orEmpty()
                )
            )
            updateUserName(userName)
            updatePassword("GmailUser")

        } catch (e: ApiException) {
            Log.d("TAG", e.message.toString())
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