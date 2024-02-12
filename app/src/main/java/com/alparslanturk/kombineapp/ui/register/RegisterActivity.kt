package com.alparslanturk.kombineapp.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityRegisterBinding
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.login.LoginActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnRegister.setOnClickListener {
                setupObservers()
                viewModel.register(
                    RegisterRequest(
                        name = edtName.text.toString(),
                        surname = edtSurname.text.toString(),
                        username = edtUserName.text.toString(),
                        password = edtPassword.text.toString(),
                        email = edtMail.text.toString()
                    )
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                registerFlow.collect {
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

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(this@RegisterActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }
}