package com.alparslanturk.kombineapp.ui.teamdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Club
import com.alparslanturk.kombineapp.data.entities.models.Match
import com.alparslanturk.kombineapp.data.entities.models.MessageEvent
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.ActivityTeamDetailBinding
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.kombineapp.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.kombineapp.ui.adapters.TicketsAdapter
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.matchdetail.MatchDetailActivity
import com.alparslanturk.kombineapp.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.kombineapp.utils.getExtrazz
import com.alparslanturk.kombineapp.utils.setGone
import com.alparslanturk.kombineapp.utils.setVisible
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class TeamDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityTeamDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TeamDetailViewModel>()

    private val upcomingMatchesAdapter by lazy { UpcomingMatchesAdapter { navigateToMatchDetail(it) } }

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    private var isAnyUpdate: Boolean = false

    private lateinit var team: Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        team = intent.getExtrazz("EXTRAS_TEAM")

        setupToolbar()
        setupObservers()
        setupRecyclerViews()
        setupLists()
    }


    private fun setupToolbar() {
        with(binding.toolbar) {
            setBackButton { onBackPressedDispatcher.onBackPressed() }
            team.apply {
                setTitle(name)
                setFavouriteButton(clubIsFavourite) {
                    if (clubIsFavourite) {
                        viewModel.removeFavouriteClub(RemoveFavouriteClubRequest(getUserID(), id))
                    } else {
                        viewModel.addFavouriteClub(AddFavouriteClubRequest(getUserID(), id))
                    }
                }
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    addFavouriteClubFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    isAnyUpdate = true
                                    EventBus.getDefault().post(MessageEvent("Favourite Update"))
                                    Toast.makeText(this@TeamDetailActivity, "Başarıyla favorilere eklendi", Toast.LENGTH_LONG).show()
                                }
                            }
                            is Result.Auth -> {}
                        }
                    }
                }

                launch {
                    removeFavouriteClubFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    isAnyUpdate = true
                                    EventBus.getDefault().post(MessageEvent("Favourite Update"))
                                    Toast.makeText(this@TeamDetailActivity, "Başarıyla favorilerden kaldırıldı", Toast.LENGTH_LONG).show()
                                }
                            }
                            is Result.Auth -> {}
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvUpcomingMatches.apply {
                adapter = upcomingMatchesAdapter
                layoutManager = LinearLayoutManager(this@TeamDetailActivity, LinearLayoutManager.VERTICAL, false)

            }
            rvTickets.apply {
                adapter = ticketsAdapter
                layoutManager = LinearLayoutManager(this@TeamDetailActivity, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun setupLists() {
        with(binding) {
            tvEmptyUpcomingMatches.apply { if (team.matchList.orEmpty().isEmpty()) setVisible() else setGone() }
            tvEmptyTickets.apply { if (team.ticketList.orEmpty().isEmpty()) setVisible() else setGone() }
        }

        upcomingMatchesAdapter.updateAdapter(team.matchList.orEmpty())
        ticketsAdapter.updateAdapter(team.ticketList.orEmpty())
    }

    private fun navigateToMatchDetail(match: Match) = startActivity(MatchDetailActivity.createIntent(this, match))

    private fun navigateToTicketDetail(ticket: Ticket) = startActivity(TicketDetailActivity.createIntent(this, ticket))

    companion object {

        private const val EXTRAS_TEAM = "EXTRAS_TEAM"

        fun createIntent(context: Context, team: Club): Intent {
            return Intent(context, TeamDetailActivity::class.java).apply {
                putExtra(EXTRAS_TEAM, team)
            }
        }
    }
}