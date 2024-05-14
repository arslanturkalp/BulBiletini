package com.alparslanturk.bulbiletini.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Club
import com.alparslanturk.bulbiletini.data.entities.models.MessageEvent
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Ticket
import com.alparslanturk.bulbiletini.databinding.FragmentHomeBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.bulbiletini.ui.adapters.HomeTeamsAdapter
import com.alparslanturk.bulbiletini.ui.adapters.TicketsAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseFragment
import com.alparslanturk.bulbiletini.ui.home.createticket.CreateTicketActivity
import com.alparslanturk.bulbiletini.ui.main.MainActivity
import com.alparslanturk.bulbiletini.ui.teamdetail.TeamDetailActivity
import com.alparslanturk.bulbiletini.ui.teams.TeamsActivity
import com.alparslanturk.bulbiletini.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.bulbiletini.utils.setVisible
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<HomeViewModel>()

    private val teamsAdapter by lazy { HomeTeamsAdapter { navigateToTeamDetail(it) } }

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    private var clubList: ArrayList<Club> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerViews()
        setupSwipeRefresh()
        setupObservers()
        setupUI()

        requireActivity().intent.apply {
            if (data != null && action == Intent.ACTION_VIEW) {
                println("url:" + data?.query.orEmpty().substringAfter("ticketID="))
            }
        }

        viewModel.loginTest()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.showcase))
        }
    }

    private fun setupRecyclerViews() {
        with(binding) {
            rvTeams.apply {
                adapter = teamsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (newState == SCROLL_STATE_DRAGGING) {
                            swipeRefreshLayout.isEnabled = false
                        }
                        if (newState == SCROLL_STATE_IDLE) {
                            swipeRefreshLayout.isEnabled = true
                        }
                    }
                })
            }

            rvTickets.apply {
                adapter = ticketsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun setupSwipeRefresh() = binding.swipeRefreshLayout.setOnRefreshListener(this)

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch(Dispatchers.Main) {
                    clubsGetListWithTicketsFlow.collect {
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
                                binding.llContent.setVisible()
                                it.body?.apply {
                                    if (code == 200) {
                                        data.apply {
                                            this@HomeFragment.clubList = ArrayList(clubList.orEmpty())
                                            teamsAdapter.updateAdapter(clubList.orEmpty().sortedByDescending { club -> club.totalTicketCount })
                                            ticketsAdapter.updateAdapter(ticketList.orEmpty())

                                            viewModel.getUserMessages(getUserID())
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    getUserMessagesFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                dismissProgress()
                                it.body?.apply {
                                    if (code == 200) {
                                        data.apply {
                                            wasNotSeenTotalMessageCount.apply { if (this != 0) (activity as MainActivity).setNotificationBadge(this) }
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    loginTestFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupUI() {
        with(binding) {
            tvSeeAll.setOnClickListener { navigateToTeams(clubList) }
            fabAdd.setOnClickListener { navigateToCreateTicket() }
        }

        viewModel.getClubsAndTickets(ClubGetListWithTicketsRequest(getUserID(), 1))

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialogTheme(
                        getString(R.string.error),
                        getString(R.string.app_exit),
                        showNegativeButton = true,
                        onPositiveButtonClick = { activity?.finishAffinity() })
                }
            })
    }

    private fun showProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun navigateToCreateTicket() = resultRefreshList.launch(CreateTicketActivity.createIntent(requireContext()))

    private val resultRefreshList = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.getClubsAndTickets(ClubGetListWithTicketsRequest(getUserID(), 1))
        }
    }

    private fun navigateToTeams(clubList: ArrayList<Club>) = startActivity(TeamsActivity.createIntent(requireContext(), clubList))

    private fun navigateToTeamDetail(team: Club) = startActivity(TeamDetailActivity.createIntent(requireContext(), team))

    private fun navigateToTicketDetail(ticket: Ticket) = resultRefreshList.launch(TicketDetailActivity.createIntent(requireContext(), ticket))

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.message == "Favourite Update") {
            viewModel.getClubsAndTickets(ClubGetListWithTicketsRequest(getUserID(), 1))
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
        viewModel.getClubsAndTickets(ClubGetListWithTicketsRequest(getUserID(), 1))
    }
}