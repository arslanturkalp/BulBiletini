package com.alparslanturk.kombineapp.ui.verificationcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityVerificationCodeBinding
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.forgotpassword.ForgotPasswordActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationCodeActivity : BaseActivity() {

    private val binding by lazy { ActivityVerificationCodeBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<VerificationCodeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupObservers()

        binding.apply {
            btnGetVerificationCode.setOnClickListener {
                viewModel.getVerificationCode(email = edtMail.text.toString())
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                verificationCodeFlow.collect {
                    when (it) {
                        is Result.Error -> {
                            showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {
                            if (it.code == 300) {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            } else {
                                if (it.body!!.data.isMailSent) {
                                    navigateToForgotPassword()
                                }
                            }
                        }
                        is Result.Auth -> {}
                    }
                }
            }
        }
    }

    private fun navigateToForgotPassword() = startActivity(ForgotPasswordActivity.createIntent(this@VerificationCodeActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, VerificationCodeActivity::class.java)
        }
    }
}