package com.alparslanturk.kombineapp.ui.ticketdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.Constants.BASE_URL
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.enums.DateFormatType
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.ActivityTicketDetailBinding
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.addOnBackPressedListener
import com.alparslanturk.kombineapp.utils.getExtrazz
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import com.alparslanturk.kombineapp.utils.toDate
import com.alparslanturk.kombineapp.utils.toString
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityTicketDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TicketDetailViewModel>()

    private var isAnyUpdate: Boolean = false

    private lateinit var ticket: Ticket

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addOnBackPressedListener { onBackClicked() }

        ticket = intent.getExtrazz("EXTRAS_TICKET")

        setupToolbar()
        setupTicket()
        setupObservers()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.ticket_detail))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
            ticket.apply {
                setFavouriteButton(ticketIsFavourite) {
                    if (ticketIsFavourite) {
                        viewModel.removeFavouriteTicket(RemoveFavouriteTicketRequest(getUserID(), ticketId))
                    } else {
                        viewModel.addFavouriteTicket(AddFavouriteTicketRequest(getUserID(), ticketId))
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTicket() {
        val matchDate = ticket.matchDate.toDate(dateFormatType = DateFormatType.DATE_TIME)?.toString(dateFormatType = DateFormatType.DATE_WITH_DOT)
        with(binding) {
            ivHomeLogo.apply { Glide.with(context).load("${BASE_URL}${ticket.homeTeamLogo}").into(this) }
            ivAwayLogo.apply { Glide.with(context).load("${BASE_URL}${ticket.awayTeamLogo}").into(this) }
            ivTournament.apply { Glide.with(context).load("${BASE_URL}${ticket.leagueImagePath}").into(this) }
            tvHome.text = ticket.homeTeamName
            tvAway.text = ticket.awayTeamName
            tvStadium.text = ticket.homeTeamStadium
            tvDate.text = matchDate
            //tvWeather.text = ticket.weather

            tvSeller.text = ticket.user.name + " " + ticket.user.surname
            tvPrice.text = String.format(getString(R.string.tl_format), ticket.price.toString())
            tvLocation.text = ticket.location
            tvComment.text = ticket.ticketDescription
            ratingBar.rating = ticket.user.rating.toFloat()

            flCall.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL).also { it.data = Uri.parse("tel:+90" + ticket.user.phoneNumber) }) }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    addFavouriteTicketFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    Toast.makeText(this@TicketDetailActivity, "Başarıyla favorilere eklendi", Toast.LENGTH_LONG).show()
                                }
                            }
                            is Result.Auth -> {}
                        }
                    }
                }

                launch {
                    removeFavouriteTicketFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    Toast.makeText(this@TicketDetailActivity, "Başarıyla favorilerden kaldırıldı", Toast.LENGTH_LONG).show()
                                }
                            }
                            is Result.Auth -> {}
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

        private const val EXTRAS_TICKET = "EXTRAS_TICKET"

        fun createIntent(context: Context, ticket: Ticket): Intent {
            return Intent(context, TicketDetailActivity::class.java).apply {
                putExtra(EXTRAS_TICKET, ticket)
            }
        }
    }
}