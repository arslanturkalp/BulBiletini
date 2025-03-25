package com.alparslanturk.bulbiletini.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.BulBiletini.Companion.TAG
import com.alparslanturk.bulbiletini.application.SessionManager.getPassword
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.data.entities.models.Club
import com.alparslanturk.bulbiletini.data.entities.models.MessageEvent
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Ticket
import com.alparslanturk.bulbiletini.databinding.FragmentHomeBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetDetailWithClubIdRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.club.ClubGetListWithTicketsRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateNotificationTokenRequest
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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
        requestToken()
        checkPermissions()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Home", HomeFragment::class.java.name)
    }

    private fun requestToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                viewModel.updateNotificationToken(UpdateNotificationTokenRequest(getUserID(), token))
            })
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

                                            clubList.orEmpty().forEach { club -> viewModel.getClubDetail(ClubGetDetailWithClubIdRequest(club.id, getUserID())) }

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
                                viewModel.signIn(LoginRequest(getUserName(), getPassword()))
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {}
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    loginFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                dismissProgress()
                            }
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    updateNotificationTokenFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                dismissProgress()
                            }
                        }
                    }
                }

                launch(Dispatchers.Main) {
                    getClubDetailFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                dismissProgress()
                                it.body?.apply {
                                    if (code == 200) {
                                        clubList.find { club -> club.id == data.id }?.apply {
                                            totalTicketCount = data.matchList.orEmpty().sumOf { match -> match.ticketCount }
                                            teamsAdapter.updateAdapter(clubList.sortedByDescending { club -> club.totalTicketCount })
                                        }
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

    private fun checkPermissions() {
        if (checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), getRequiredPermissions(), 1)
        }
    }

    @SuppressLint("InlinedApi")
    private fun getRequiredPermissions(): Array<String> = when (isNotificationPermissionRequired()) {
        true -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.POST_NOTIFICATIONS)
        false -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun isNotificationPermissionRequired() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED else false

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