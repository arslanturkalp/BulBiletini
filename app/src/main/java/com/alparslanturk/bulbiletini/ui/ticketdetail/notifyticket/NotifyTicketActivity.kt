package com.alparslanturk.bulbiletini.ui.ticketdetail.notifyticket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.custom.selectionbottomsheet.SelectionBottomSheetDialog
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.SelectionDialogItem
import com.alparslanturk.bulbiletini.databinding.ActivityNotifyTicketBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.NotifyTicketRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.show
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotifyTicketActivity : BaseActivity() {

    private val binding by lazy { ActivityNotifyTicketBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<NotifyTicketViewModel>()

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

        with(binding) {
            btnNotifyTicket.setOnClickListener { showOptionDialog() }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.notify_advert))
            setBackButton { onBackClicked() }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    notifyTicketFlow.collect {
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
                                        Toast.makeText(this@NotifyTicketActivity, getString(R.string.ticket_notified_successful), Toast.LENGTH_LONG).show()
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

    private fun showOptionDialog() {
        val ticketID = intent.getDataExtra<String>(EXTRAS_DATA_TICKET_ID)

        SelectionBottomSheetDialog(
            title = getString(R.string.notify_advert),
            list = listOf(
                SelectionDialogItem(getString(R.string.yes), "0"),
                SelectionDialogItem(getString(R.string.no), "1"),
            ),
            onItemSelected = {
                when (it.id) {
                    "0" -> viewModel.notifyTicket(NotifyTicketRequest(getUserID(), ticketID, binding.edtMessage.text.toString()))
                    "1" -> dismissProgressDialog()
                }
            }
        ).show(this)
    }

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        private const val EXTRAS_DATA_TICKET_ID = "EXTRAS_DATA_TICKET_ID"

        fun createIntent(context: Context, ticketID: String): Intent {
            return Intent(context, NotifyTicketActivity::class.java).apply {
                putExtra(EXTRAS_DATA_TICKET_ID, ticketID)
            }
        }
    }
}