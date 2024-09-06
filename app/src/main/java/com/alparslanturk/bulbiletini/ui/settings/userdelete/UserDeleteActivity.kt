package com.alparslanturk.bulbiletini.ui.settings.userdelete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getMail
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.custom.selectionbottomsheet.SelectionBottomSheetDialog
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.SelectionDialogItem
import com.alparslanturk.bulbiletini.databinding.ActivityUserDeleteBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.login.LoginActivity
import com.alparslanturk.bulbiletini.utils.show
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
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

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("User Delete Page", UserDeleteActivity::class.java.name)
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