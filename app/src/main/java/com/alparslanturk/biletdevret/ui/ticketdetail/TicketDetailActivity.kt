package com.alparslanturk.biletdevret.ui.ticketdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.Constants.BASE_URL
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.data.entities.enums.DateFormatType
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.Ticket
import com.alparslanturk.biletdevret.databinding.ActivityTicketDetailBinding
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.messages.chat.ChatActivity
import com.alparslanturk.biletdevret.ui.userdetail.UserDetailActivity
import com.alparslanturk.biletdevret.utils.addOnBackPressedListener
import com.alparslanturk.biletdevret.utils.getDataExtra
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import com.alparslanturk.biletdevret.utils.toDate
import com.alparslanturk.biletdevret.utils.toString
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityTicketDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TicketDetailViewModel>()

    private var isAnyUpdate: Boolean = false

    private var isBlockedUser: Boolean = false

    private lateinit var ticket: Ticket

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addOnBackPressedListener { onBackClicked() }

        ticket = intent.getDataExtra("EXTRAS_TICKET")

        setupToolbar()
        setupTicket()
        setupObservers()

        viewModel.getBlockedUsers(getUserID())
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.ticket_detail))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
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
            ratingBar.rating = (ticket.user.rating ?: 0.0).toFloat()

            ivAddFavourite.apply {
                if (ticket.ticketIsFavourite) {
                    setOnClickListener { viewModel.removeFavouriteTicket(RemoveFavouriteTicketRequest(getUserID(), ticket.ticketId)) }
                    setImageResource(R.drawable.ic_favourite_black)
                } else {
                    setOnClickListener { viewModel.addFavouriteTicket(AddFavouriteTicketRequest(getUserID(), ticket.ticketId)) }
                    setImageResource(R.drawable.ic_not_favourite_black)
                }
            }

            flMessage.setOnClickListener { navigateToChat() }
            flCall.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL).also { it.data = Uri.parse("tel:+90" + ticket.user.phoneNumber) }) }
            llTicketUser.setOnClickListener { navigateToUserDetail() }
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
                                it.body?.apply {
                                    if (code == 200) {
                                        Toast.makeText(this@TicketDetailActivity, getString(R.string.successfully_added_favourites), Toast.LENGTH_LONG).show()
                                        ticket.ticketIsFavourite = true
                                        isAnyUpdate = true
                                        setupTicket()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
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
                                it.body?.apply {
                                    if (code == 200) {
                                        Toast.makeText(this@TicketDetailActivity, getString(R.string.successfully_removed_favourites), Toast.LENGTH_LONG).show()
                                        ticket.ticketIsFavourite = false
                                        isAnyUpdate = true
                                        setupTicket()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    getBlockedUsersFlow.collect {
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
                                        isBlockedUser = data.blockedUserList.any { blockedUser -> blockedUser.id == ticket.user.id }
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

    private fun navigateToChat() = startActivity(ChatActivity.createIntent(this, ticket.user))

    private fun navigateToUserDetail() = startActivity(UserDetailActivity.createIntent(this, ticket.user, isBlockedUser))

    companion object {

        private const val EXTRAS_TICKET = "EXTRAS_TICKET"

        fun createIntent(context: Context, ticket: Ticket): Intent {
            return Intent(context, TicketDetailActivity::class.java).apply {
                putExtra(EXTRAS_TICKET, ticket)
            }
        }
    }
}