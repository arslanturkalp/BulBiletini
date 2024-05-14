package com.alparslanturk.bulbiletini.ui.userdetail.rateuser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivityRateUserBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.userrate.RateUserRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RateUserActivity : BaseActivity() {

    private val binding by lazy { ActivityRateUserBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<RateUserViewModel>()

    private var isAnyUpdate: Boolean = false

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()

        val userID = intent.getDataExtra<String>(EXTRAS_DATA_USER_ID)

        with(binding) {
            btnRateUser.setOnClickListener {
                viewModel.rateUser(RateUserRequest(userID, getUserID(), ratingBar.rating.toDouble()))
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.rate_user))
            setBackButton { onBackClicked() }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    rateUserFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgressDialog()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgressDialog()
                                it.body?.apply {
                                    if (code == 200) {
                                        isAnyUpdate = true
                                        returnResult()
                                        Toast.makeText(this@RateUserActivity, getString(R.string.rate_added_successful), Toast.LENGTH_LONG).show()
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

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        private const val EXTRAS_DATA_USER_ID = "EXTRAS_DATA_USER_ID"

        fun createIntent(context: Context, userID: String): Intent {
            return Intent(context, RateUserActivity::class.java).apply {
                putExtra(EXTRAS_DATA_USER_ID, userID)
            }
        }
    }
}