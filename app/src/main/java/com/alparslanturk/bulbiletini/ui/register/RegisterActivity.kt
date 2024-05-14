package com.alparslanturk.bulbiletini.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivityRegisterBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.login.LoginActivity
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()
        setupUI()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.register))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
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
                    }
                }
            }
        }
    }

    private fun setupUI() {
        binding.apply {
            btnRegister.setOnClickListener {
                validateRegister(edtName.text.toString(), edtSurname.text.toString(), edtUserName.text.toString().trim(), edtPassword.text.toString().trim(), edtPasswordAgain.text.toString().trim(), edtMail.text.toString().trim())
                when (viewModel.getErrorList().isEmpty()) {
                    true ->
                        viewModel.register(
                            RegisterRequest(
                                name = edtName.text.toString(),
                                surname = edtSurname.text.toString(),
                                username = edtUserName.text.toString(),
                                password = edtPassword.text.toString(),
                                email = edtMail.text.toString()
                            )
                        )
                    false -> showAlertDialogTheme(getString(R.string.error), viewModel.getErrorList().joinToString(separator = "\n") { warning -> getString(warning) })
                }
            }
        }
    }

    private fun validateRegister(name: String, surname: String, userName: String, password: String, passwordAgain: String, mail: String) {
        viewModel.clearErrorList()

        binding.apply {
            if (name.isEmpty()) {
                viewModel.addError(R.string.name_empty_error)
            }
            if (surname.isEmpty()) {
                viewModel.addError(R.string.surname_empty_error)
            }
            if (userName.isEmpty()) {
                viewModel.addError(R.string.user_name_empty_error)
            }
            if (password.isEmpty()) {
                viewModel.addError(R.string.password_empty_error)
                if (passwordAgain.isEmpty()) {
                    viewModel.addError(R.string.password_again_empty_error)
                }
            }
            if (password != passwordAgain) {
                viewModel.addError(R.string.passwords_not_match)
            }
            if (mail.isEmpty()) {
                viewModel.addError(R.string.mail_empty_error)
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