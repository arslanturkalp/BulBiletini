package com.alparslanturk.biletdevret.ui.login.verificationcode

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.databinding.ActivityVerificationCodeBinding
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.login.forgotpassword.ForgotPasswordActivity
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationCodeActivity : BaseActivity() {

    private val binding by lazy { ActivityVerificationCodeBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<VerificationCodeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()

        binding.apply {
            btnGetVerificationCode.setOnClickListener {
                viewModel.getVerificationCode(email = edtMail.text.toString())
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.get_verification_code))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    verificationCodeFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgressDialog()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {
                                if (it.isLoading) showProgressDialog()
                            }
                            is Result.Success -> {
                                dismissProgressDialog()
                                it.body?.apply {
                                    if (code == 200) {
                                        if (data.isMailSent) navigateToForgotPassword()
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

    private fun navigateToForgotPassword() = startActivity(ForgotPasswordActivity.createIntent(this@VerificationCodeActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, VerificationCodeActivity::class.java)
        }
    }
}