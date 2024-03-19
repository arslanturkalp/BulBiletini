package com.alparslanturk.biletdevret.ui.teamdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.data.entities.models.Club
import com.alparslanturk.biletdevret.data.entities.models.Match
import com.alparslanturk.biletdevret.data.entities.models.MessageEvent
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.Ticket
import com.alparslanturk.biletdevret.databinding.ActivityTeamDetailBinding
import com.alparslanturk.biletdevret.domain.entities.requests.club.ClubGetDetailWithClubIdRequest
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.AddFavouriteClubRequest
import com.alparslanturk.biletdevret.domain.entities.requests.favourite.RemoveFavouriteClubRequest
import com.alparslanturk.biletdevret.ui.adapters.TicketsAdapter
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.matchdetail.MatchDetailActivity
import com.alparslanturk.biletdevret.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.biletdevret.utils.addOnBackPressedListener
import com.alparslanturk.biletdevret.utils.getDataExtra
import com.alparslanturk.biletdevret.utils.setGone
import com.alparslanturk.biletdevret.utils.setVisible
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class TeamDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityTeamDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TeamDetailViewModel>()

    private val upcomingMatchesAdapter by lazy { UpcomingMatchesAdapter { navigateToMatchDetail(it) } }

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    private var isAnyUpdate: Boolean = false

    private lateinit var team: Club

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addOnBackPressedListener { onBackClicked() }

        team = intent.getDataExtra("EXTRAS_TEAM")

        setupToolbar()
        setupObservers()
        setupRecyclerViews()

        viewModel.getClubDetail(ClubGetDetailWithClubIdRequest(team.id, getUserID()))
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
                launch(Dispatchers.Main) {
                    getClubDetailFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                it.body?.apply {
                                    if (code == 200) {
                                        setupLists(data.matchList.orEmpty(), data.ticketList.orEmpty())
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    addFavouriteClubFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                it.body?.apply {
                                    if (code == 200) {
                                        isAnyUpdate = true
                                        EventBus.getDefault().post(MessageEvent("Favourite Update"))
                                        Toast.makeText(this@TeamDetailActivity, getString(R.string.successfully_added_favourites), Toast.LENGTH_LONG).show()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    removeFavouriteClubFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                it.body?.apply {
                                    if (code == 200) {
                                        isAnyUpdate = true
                                        EventBus.getDefault().post(MessageEvent("Favourite Update"))
                                        Toast.makeText(this@TeamDetailActivity, getString(R.string.successfully_removed_favourites), Toast.LENGTH_LONG).show()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                    }
                                }
                            }
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

    private fun setupLists(matchList: List<Match>, ticketList: List<Ticket>) {
        with(binding) {
            tvEmptyUpcomingMatches.apply { if (matchList.isEmpty()) setVisible() else setGone() }
            tvEmptyTickets.apply { if (ticketList.isEmpty()) setVisible() else setGone() }
        }

        upcomingMatchesAdapter.updateAdapter(matchList)
        ticketsAdapter.updateAdapter(ticketList)
    }

    private fun navigateToMatchDetail(match: Match) = startActivity(MatchDetailActivity.createIntent(this, match))

    private fun navigateToTicketDetail(ticket: Ticket) = startActivity(TicketDetailActivity.createIntent(this, ticket))

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        private const val EXTRAS_TEAM = "EXTRAS_TEAM"

        fun createIntent(context: Context, team: Club): Intent {
            return Intent(context, TeamDetailActivity::class.java).apply {
                putExtra(EXTRAS_TEAM, team)
            }
        }
    }
}