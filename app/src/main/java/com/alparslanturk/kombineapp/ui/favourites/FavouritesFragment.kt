package com.alparslanturk.kombineapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Team
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.FragmentFavouritesBinding
import com.alparslanturk.kombineapp.ui.base.BaseFragment
import com.alparslanturk.kombineapp.ui.home.adapters.HomeTeamsAdapter
import com.alparslanturk.kombineapp.ui.home.adapters.TicketsAdapter
import com.alparslanturk.kombineapp.ui.teamdetail.TeamDetailActivity
import com.alparslanturk.kombineapp.ui.ticketdetail.TicketDetailActivity

class FavouritesFragment : BaseFragment() {

    private val binding by lazy { FragmentFavouritesBinding.inflate(layoutInflater) }

    private val teamsAdapter by lazy { HomeTeamsAdapter { navigateToTeamDetail(it) } }

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerViews()
        setupTeamsList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.favourites))
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvTeams.apply {
                adapter = teamsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            rvTickets.apply {
                adapter = ticketsAdapter
            }
        }
    }

    private fun setupTeamsList() {
        val list = listOf(
            Team("1", "Fenerbahçe", "#FFED00", "#163962", "https://seeklogo.com/images/F/fenerbahce-spor-kulubu-5-yildizli-arma-logo-64F337AD4A-seeklogo.com.png", "100"),
        )
        teamsAdapter.updateAdapter(list)
    }

    private fun navigateToTeamDetail(team: Team) = startActivity(TeamDetailActivity.createIntent(requireContext(), team))

    private fun navigateToTicketDetail(ticket: Ticket) = startActivity(TicketDetailActivity.createIntent(requireContext(), ticket))
}