package com.alparslanturk.bulbiletini.ui.settings.suggestionandcomplaint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivitySuggestionAndComplaintBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.suggestionandcomplaint.SendSuggestionAndComplaintRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SuggestionAndComplaintActivity : BaseActivity() {

    private val binding by lazy { ActivitySuggestionAndComplaintBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SuggestionAndComplaintViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()

        binding.apply {
            btnSend.setOnClickListener { viewModel.sendSuggestionAndComplaint(SendSuggestionAndComplaintRequest(getUserID(), edtSuggestionOrComplaint.text.toString())) }
        }
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Send Suggestion Page", SuggestionAndComplaintActivity::class.java.name)
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.send_suggestion_and_complaint))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    sendSuggestionAndComplaintFlow.collect {
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
                                        showAlertDialogTheme(getString(R.string.success), getString(R.string.successfully_sended_suggestion), onPositiveButtonClick = {onBackPressedDispatcher.onBackPressed()})
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

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SuggestionAndComplaintActivity::class.java)
        }
    }
}