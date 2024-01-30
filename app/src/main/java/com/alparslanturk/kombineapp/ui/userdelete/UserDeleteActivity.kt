package com.alparslanturk.kombineapp.ui.userdelete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityUserDeleteBinding
import com.alparslanturk.kombineapp.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.login.LoginActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDeleteActivity : BaseActivity() {

    private val binding by lazy { ActivityUserDeleteBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UserDeleteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupObservers()

        binding.apply {
            btnDeleteUser.setOnClickListener {
                viewModel.deleteUser(
                    UserDeleteRequest(
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
                launch {
                    userDeleteFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
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
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(this@UserDeleteActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, UserDeleteActivity::class.java)
        }
    }
}