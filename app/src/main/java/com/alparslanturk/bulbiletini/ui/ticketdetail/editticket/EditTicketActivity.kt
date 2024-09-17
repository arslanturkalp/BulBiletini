package com.alparslanturk.bulbiletini.ui.ticketdetail.editticket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.Constants.BASE_URL
import com.alparslanturk.bulbiletini.data.entities.enums.DateFormatType
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Ticket
import com.alparslanturk.bulbiletini.databinding.ActivityEditTicketBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.UpdateTicketRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import com.alparslanturk.bulbiletini.utils.toDate
import com.alparslanturk.bulbiletini.utils.toString
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTicketActivity : BaseActivity() {

    private val binding by lazy { ActivityEditTicketBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<EditTicketViewModel>()

    private var isAnyUpdate: Boolean = false

    private lateinit var ticket: Ticket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ticket = intent.getDataExtra("EXTRAS_TICKET")

        setupToolbar()
        setupObservers()
        setupUI()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.edit_ticket))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    updateTicketFlow.collect {
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
                                        Toast.makeText(this@EditTicketActivity, getString(R.string.ticket_edited_successful), Toast.LENGTH_LONG).show()
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

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        val matchDate = ticket.matchDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.DATE_WITH_DOT)

        with(binding) {
            ivHomeLogo.apply { Glide.with(context).load("$BASE_URL${ticket.homeTeamLogo}").into(this) }
            ivAwayLogo.apply { Glide.with(context).load("$BASE_URL${ticket.awayTeamLogo}").into(this) }
            ivTournament.apply { Glide.with(context).load("$BASE_URL${ticket.leagueImagePath}").into(this) }
            tvHome.text = ticket.homeTeamName
            tvAway.text = ticket.awayTeamName
            tvStadium.text = ticket.homeTeamStadium
            tvDate.text = matchDate

            etPrice.setText(ticket.price.toString())
            etComment.setText(ticket.ticketDescription)

            llEdit.setOnClickListener {
                if (etPrice.text.toString().isEmpty()) {
                    showAlertDialogTheme(getString(R.string.warning), getString(R.string.please_enter_price))
                }
                if (etComment.text.toString().isEmpty()) {
                    showAlertDialogTheme(getString(R.string.warning), getString(R.string.please_enter_description))
                }
                if (etPrice.text.toString().isNotEmpty() && etComment.text.toString().isNotEmpty()) {
                    viewModel.updateTicket(
                        UpdateTicketRequest(
                            ticketId = ticket.ticketId,
                            newPrice = etPrice.text.toString().toInt(),
                            newDescription = etComment.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        private const val EXTRAS_TICKET = "EXTRAS_TICKET"

        fun createIntent(context: Context, ticket: Ticket): Intent {
            return Intent(context, EditTicketActivity::class.java).apply {
                putExtra(EXTRAS_TICKET, ticket)
            }
        }
    }
}

