package com.alparslanturk.kombineapp.ui.ticketdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.ActivityDetailBinding
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.getExtrazz
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupTicket()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.ticket_detail))
            setBackButton { onBackPressed() }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTicket() {
        val ticket = intent.getExtrazz<Ticket>("EXTRAS_TICKET")
        with(binding) {
            ivHomeLogo.apply { Glide.with(context).load(ticket.homeLogo).into(this) }
            ivAwayLogo.apply { Glide.with(context).load(ticket.awayLogo).into(this) }
            ivTournament.apply { Glide.with(context).load(ticket.league).into(this) }
            tvHome.text = ticket.homeTeam
            tvAway.text = ticket.awayTeam
            tvStadium.text = ticket.stadium
            tvDate.text = ticket.matchDate
            tvWeather.text = ticket.weather

            tvSeller.text = ticket.user.name + " " + ticket.user.surname
            tvPrice.text = ticket.price
            tvLocation.text = ticket.location
            tvComment.text = ticket.comment
            ratingBar.rating = ticket.user.rating.toFloat()

            flCall.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL).also { it.data = Uri.parse("tel:+90" + ticket.user.phoneNumber) }) }
        }
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