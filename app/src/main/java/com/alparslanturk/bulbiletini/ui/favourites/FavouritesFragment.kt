package com.alparslanturk.bulbiletini.ui.favourites

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
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Club
import com.alparslanturk.bulbiletini.data.entities.models.MessageEvent
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Ticket
import com.alparslanturk.bulbiletini.databinding.FragmentFavouritesBinding
import com.alparslanturk.bulbiletini.ui.adapters.HomeTeamsAdapter
import com.alparslanturk.bulbiletini.ui.adapters.TicketsAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseFragment
import com.alparslanturk.bulbiletini.ui.main.MainActivity
import com.alparslanturk.bulbiletini.ui.teamdetail.TeamDetailActivity
import com.alparslanturk.bulbiletini.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.bulbiletini.utils.setGone
import com.alparslanturk.bulbiletini.utils.setVisible
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Favourites Page", FavouritesFragment::class.java.name)
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
                                it.body?.apply {
                                    if (code == 200) {
                                        it.body.data.apply {
                                            teamsAdapter.updateAdapter(clubList.orEmpty())
                                            ticketsAdapter.updateAdapter(ticketList.orEmpty())
                                            with(binding) {
                                                if (clubList?.isEmpty() == true) {
                                                    tvNotFavouriteTeam.setVisible()
                                                    rvTeams.setGone()
                                                } else {
                                                    tvNotFavouriteTeam.setGone()
                                                    rvTeams.setVisible()
                                                }
                                                if (ticketList?.isEmpty() == true) {
                                                    tvNotFavouriteTicket.setVisible()
                                                    rvTickets.setGone()
                                                } else {
                                                    tvNotFavouriteTicket.setGone()
                                                    rvTickets.setVisible()
                                                }
                                            }

                                        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.message == "Favourite Update") {
            viewModel.getFavourites(getUserID())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onRefresh() {
        viewModel.getFavourites(getUserID())
    }
}