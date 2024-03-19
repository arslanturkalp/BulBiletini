package com.alparslanturk.biletdevret.ui.settings.userdelete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getMail
import com.alparslanturk.biletdevret.application.SessionManager.getUserName
import com.alparslanturk.biletdevret.custom.selectionbottomsheet.SelectionBottomSheetDialog
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.SelectionDialogItem
import com.alparslanturk.biletdevret.databinding.ActivityUserDeleteBinding
import com.alparslanturk.biletdevret.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.login.LoginActivity
import com.alparslanturk.biletdevret.utils.show
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDeleteActivity : BaseActivity() {

    private val binding by lazy { ActivityUserDeleteBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UserDeleteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()

        binding.apply {
            btnDeleteUser.setOnClickListener { showOptionDialog() }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.delete_account))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
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
                                it.body?.apply {
                                    if (code == 200) {
                                        navigateToLogin()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showOptionDialog() {
        SelectionBottomSheetDialog(
            title = getString(R.string.delete_account),
            list = listOf(
                SelectionDialogItem(getString(R.string.yes), "0"),
                SelectionDialogItem(getString(R.string.no), "1"),
            ),
            onItemSelected = {
                when (it.id) {
                    "0" -> viewModel.deleteUser(
                        UserDeleteRequest(
                            username = getUserName(),
                            password = binding.edtPassword.text.toString(),
                            email = getMail()
                        )
                    )
                    "1" -> dismissProgressDialog()
                }
            }
        ).show(this)
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(this@UserDeleteActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, UserDeleteActivity::class.java)
        }
    }
}