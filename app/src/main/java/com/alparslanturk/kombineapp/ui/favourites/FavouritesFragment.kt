package com.alparslanturk.kombineapp.ui.favourites

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Club
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.FragmentFavouritesBinding
import com.alparslanturk.kombineapp.ui.adapters.HomeTeamsAdapter
import com.alparslanturk.kombineapp.ui.adapters.TicketsAdapter
import com.alparslanturk.kombineapp.ui.base.BaseFragment
import com.alparslanturk.kombineapp.ui.main.MainActivity
import com.alparslanturk.kombineapp.ui.teamdetail.TeamDetailActivity
import com.alparslanturk.kombineapp.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.kombineapp.utils.setGone
import com.alparslanturk.kombineapp.utils.setVisible
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { FragmentFavouritesBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<FavouritesViewModel>()

    private val teamsAdapter by lazy { HomeTeamsAdapter { navigateToTeamDetail(it) } }

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerViews()
        setupObservers()

        viewModel.getFavourites(getUserID())

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backToMainMenu()
                }
            })
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.favourites))
        }
    }

    private fun setupSwipeRefresh() = binding.swipeRefreshLayout.setOnRefreshListener(this)

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

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getFavouritesFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {
                                showProgress()
                            }

                            is Result.Success -> {
                                dismissProgress()
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        teamsAdapter.updateAdapter(clubList)
                                        ticketsAdapter.updateAdapter(ticketList)
                                        if (clubList.isEmpty()) binding.tvNotFavouriteTeam.setVisible() else binding.tvNotFavouriteTeam.setGone()
                                        if (ticketList.isEmpty()) binding.tvNotFavouriteTicket.setVisible() else binding.tvNotFavouriteTicket.setGone()
                                    }
                                }
                            }
                            is Result.Auth -> {}
                        }
                    }
                }
            }
        }
    }

    private fun backToMainMenu() {
        (activity as MainActivity).apply {
            showFragment(homeFragment)
            setItemInNavigation(homeFragment)
        }
    }

    private fun showProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun navigateToTeamDetail(team: Club) = resultRefreshList.launch(TeamDetailActivity.createIntent(requireContext(), team))

    private fun navigateToTicketDetail(ticket: Ticket) = resultRefreshList.launch(TicketDetailActivity.createIntent(requireContext(), ticket))

    private val resultRefreshList = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getFavourites(getUserID())
        }
    }

    override fun onRefresh() {
        viewModel.getFavourites(getUserID())
    }
}