package com.alparslanturk.bulbiletini.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.Constants.ADMIN_PASS
import com.alparslanturk.bulbiletini.application.Constants.ADMIN_USER
import com.alparslanturk.bulbiletini.application.SessionManager.getPassword
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.application.SessionManager.updatePassword
import com.alparslanturk.bulbiletini.application.SessionManager.updateRefreshToken
import com.alparslanturk.bulbiletini.application.SessionManager.updateToken
import com.alparslanturk.bulbiletini.application.SessionManager.updateUserName
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivitySplashBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.login.LoginActivity
import com.alparslanturk.bulbiletini.ui.main.MainActivity
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupObservers()
        viewModel.signIn(LoginRequest(ADMIN_USER, ADMIN_PASS), true)
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Splash Page", SplashActivity::class.java.name)
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
                                            updateToken(token.accessToken)
                                            updateRefreshToken(token.refreshToken)
                                            if (viewModel.getIsFromAdmin()) viewModel.getProjectSettings() else startActivity(MainActivity.createIntent(this@SplashActivity))
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message, onPositiveButtonClick = {
                                            updateUserName("")
                                            updatePassword("")
                                            startActivity(LoginActivity.createIntent(this@SplashActivity))
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
                launch {
                    projectSettingFlow.collect {
                        when (it) {
                            is Result.Error -> showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            is Result.Loading -> {}
                            is Result.Success -> {
                                it.body!!.apply {
                                    if (this@SplashActivity.packageManager.getPackageInfo(this@SplashActivity.packageName, 0).versionName == data.settingValue) {
                                        if (getUserName() == "" && getPassword() == "") {
                                            startActivity(LoginActivity.createIntent(this@SplashActivity))
                                        } else {
                                            viewModel.signIn(LoginRequest(getUserName(), getPassword()))
                                        }
                                    } else {
                                        viewModel.isRequiredUpdate()
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    requiredUpdateFlow.collect {
                        when (it) {
                            is Result.Error -> showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            is Result.Loading -> {}
                            is Result.Success -> {
                                it.body!!.apply {
                                    if (data.settingValue == "false") {
                                        if (getUserName() == "" && getPassword() == "") {
                                            startActivity(LoginActivity.createIntent(this@SplashActivity))
                                        } else {
                                            viewModel.signIn(LoginRequest(getUserName(), getPassword()))
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.new_version_title), contentMessage = getString(R.string.new_version_available), positiveButtonTitle = getString(R.string.download), onPositiveButtonClick = {
                                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${applicationContext.packageName}")))
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}