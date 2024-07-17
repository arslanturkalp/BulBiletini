package com.alparslanturk.bulbiletini.ui.ticketdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.Constants.BASE_URL
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.enums.DateFormatType
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Ticket
import com.alparslanturk.bulbiletini.databinding.ActivityTicketDetailBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.AddFavouriteTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.favourite.RemoveFavouriteTicketRequest
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.messages.chat.ChatActivity
import com.alparslanturk.bulbiletini.ui.ticketdetail.notifyticket.NotifyTicketActivity
import com.alparslanturk.bulbiletini.ui.userdetail.UserDetailActivity
import com.alparslanturk.bulbiletini.utils.addOnBackPressedListener
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import com.alparslanturk.bulbiletini.utils.toDate
import com.alparslanturk.bulbiletini.utils.toString
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
            ivTournament.apply { Glide.with(context).load(ticket.leagueImagePath).into(this) }
            tvHome.text = ticket.homeTeamName
            tvAway.text = ticket.awayTeamName
            tvStadium.text = ticket.homeTeamStadium
            tvDate.text = matchDate

            tvSeller.text = ticket.user.name + " " + ticket.user.surname
            tvPrice.text = String.format(getString(R.string.tl_format), ticket.price.toString())
            tvLocation.text = "${ticket.tribune} - ${ticket.block} - ${ticket.order}"
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

            ivShare.setOnClickListener {
                ShareCompat.IntentBuilder(this@TicketDetailActivity)
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.share_ticket))
                    .setText("http://www.biletdevret.com/ticketID=${ticket.ticketId}")
                    .startChooser()
            }

            flMessage.setOnClickListener { navigateToChat() }
            flCall.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL).also { it.data = Uri.parse("tel:+90" + ticket.user.phoneNumber) }) }
            llTicketUser.setOnClickListener { navigateToUserDetail() }
            llLocation.setOnClickListener { openLocationOnMap(ticket.location) }
            llNotify.setOnClickListener { navigateToNotifyTicket(ticket.ticketId) }
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

    private fun openLocationOnMap(address: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
        startActivity(intent)
    }

    private fun navigateToNotifyTicket(ticketId: String) = startActivity(NotifyTicketActivity.createIntent(this, ticketId))

    companion object {

        private const val EXTRAS_TICKET = "EXTRAS_TICKET"

        fun createIntent(context: Context, ticket: Ticket): Intent {
            return Intent(context, TicketDetailActivity::class.java).apply {
                putExtra(EXTRAS_TICKET, ticket)
            }
        }
    }
}