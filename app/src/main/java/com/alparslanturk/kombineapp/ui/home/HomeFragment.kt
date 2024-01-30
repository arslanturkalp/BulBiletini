package com.alparslanturk.kombineapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Team
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.data.entities.models.TicketUser
import com.alparslanturk.kombineapp.databinding.FragmentHomeBinding
import com.alparslanturk.kombineapp.ui.base.BaseFragment
import com.alparslanturk.kombineapp.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.kombineapp.ui.home.adapters.HomeTeamsAdapter
import com.alparslanturk.kombineapp.ui.home.adapters.TicketsAdapter
import com.alparslanturk.kombineapp.ui.teamdetail.TeamDetailActivity
import com.alparslanturk.kombineapp.ui.teams.TeamsActivity

class HomeFragment : BaseFragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

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
        setupTicketsList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.showcase))
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            tvSeeAll.setOnClickListener { navigateToTeams() }
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
            Team("2", "Galatasaray", "#FDB912", "#A90432", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "100"),
            Team("3", "Beşiktaş", "#000000", "#FFFFFF", "https://upload.wikimedia.org/wikipedia/commons/0/08/Be%C5%9Fikta%C5%9F_Logo_Be%C5%9Fikta%C5%9F_Amblem_Be%C5%9Fikta%C5%9F_Arma.png", "100"),
            Team("4", "Trabzonspor", "#A41D34", "#14C0F1", "https://upload.wikimedia.org/wikipedia/tr/archive/a/ab/20220929150220%21TrabzonsporAmblemi.png", "100"),
            Team("5", "Başakşehir", "#1E3A58", "#ED5E27", "https://upload.wikimedia.org/wikipedia/tr/c/cd/%C4%B0stanbul_Ba%C5%9Fak%C5%9Fehir_FK.png", "100")
        )
        teamsAdapter.updateAdapter(list)
    }

    private fun setupTicketsList() {
        val list = listOf(
            Ticket("1", "Galatasaray", "Kopenhag", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "https://upload.wikimedia.org/wikipedia/tr/e/ea/FC_K%C3%B8benhavn.png", "20.09.2023\nÇarşamba\n19:45", "1200₺", false, "Kuzey Alt 107-6", "Rams Park", "30°", "Bilet Kuzey Üst 422 de mevcuttur. İstenirse 2 bilet yan yana bulunur. Bilet Kuzey Üst 422 de mevcuttur. İstenirse 2 bilet yan yana bulunur.Bilet Kuzey Üst 422 de mevcuttur. İstenirse 2 bilet yan yana bulunur.", "https://assets.stickpng.com/images/5842fe06a6515b1e0ad75b3b.png", TicketUser("1", "Ömer İhsan", "Oğuz", "5231223311", true, "4.0", true)),
            Ticket("2", "Galatasaray", "Kopenhag", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "https://upload.wikimedia.org/wikipedia/tr/e/ea/FC_K%C3%B8benhavn.png", "20.09.2023", "1200₺", false, "Kuzey Alt 107-6", "Rams Park", "30°", "", "https://assets.stickpng.com/images/5842fe06a6515b1e0ad75b3b.png", TicketUser("1", "Ömer İhsan", "Oğuz", "5231223311", true, "4.0", true)),
            Ticket("3", "Galatasaray", "Kopenhag", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "https://upload.wikimedia.org/wikipedia/tr/e/ea/FC_K%C3%B8benhavn.png", "20.09.2023", "1200₺", false, "Kuzey Alt 107-6", "Rams Park", "30°", "", "https://assets.stickpng.com/images/5842fe06a6515b1e0ad75b3b.png", TicketUser("1", "Ömer İhsan", "Oğuz", "5231223311", true, "4.0", true)),
            Ticket("4", "Galatasaray", "Kopenhag", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "https://upload.wikimedia.org/wikipedia/tr/e/ea/FC_K%C3%B8benhavn.png", "20.09.2023", "1200₺", false, "Kuzey Alt 107-6", "Rams Park", "30°", "", "https://assets.stickpng.com/images/5842fe06a6515b1e0ad75b3b.png", TicketUser("1", "Ömer İhsan", "Oğuz", "5231223311", true, "4.0", true)),
            Ticket("5", "Galatasaray", "Kopenhag", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "https://upload.wikimedia.org/wikipedia/tr/e/ea/FC_K%C3%B8benhavn.png", "20.09.2023", "1200₺", false, "Kuzey Alt 107-6", "Rams Park", "30°", "", "https://assets.stickpng.com/images/5842fe06a6515b1e0ad75b3b.png", TicketUser("1", "Ömer İhsan", "Oğuz", "5231223311", true, "4.0", true))
        )
        ticketsAdapter.updateAdapter(list)
    }

    private fun navigateToTeams() = startActivity(TeamsActivity.createIntent(requireContext()))

    private fun navigateToTeamDetail(team: Team) = startActivity(TeamDetailActivity.createIntent(requireContext(), team))

    private fun navigateToTicketDetail(ticket: Ticket) = startActivity(TicketDetailActivity.createIntent(requireContext(), ticket))
}