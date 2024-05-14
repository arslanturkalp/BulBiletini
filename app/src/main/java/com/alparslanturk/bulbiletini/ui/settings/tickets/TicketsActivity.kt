package com.alparslanturk.bulbiletini.ui.settings.tickets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Ticket
import com.alparslanturk.bulbiletini.databinding.ActivityTicketsBinding
import com.alparslanturk.bulbiletini.ui.adapters.TicketsAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.setGone
import com.alparslanturk.bulbiletini.utils.setVisible
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketsActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { ActivityTicketsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TicketsViewModel>()

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerView()
        setupObservers()

        userId = intent.getDataExtra(EXTRAS_USER_ID)

        viewModel.getTickets(userId)
    }

    private fun setupToolbar() {
        val isDifferentUser = intent.extras?.getBoolean(EXTRAS_DIFFERENT_USER, false)
        binding.toolbar.apply {
            setTitle(if (isDifferentUser == true) getString(R.string.adverts) else getString(R.string.my_adverts))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupSwipeRefresh() = binding.swipeRefreshLayout.setOnRefreshListener(this)

    private fun setupRecyclerView() {
        with(binding) {
            rvTickets.apply {
                adapter = ticketsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getTicketsFlow.collect {
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
                                        data.apply {
                                            ticketsAdapter.updateAdapter(ticketList)
                                            if (ticketList.isEmpty()) binding.tvNotFavouriteTicket.setVisible() else binding.tvNotFavouriteTicket.setGone()
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

    private fun showProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun navigateToTicketDetail(ticket: Ticket) = startActivity(TicketDetailActivity.createIntent(this, ticket))

    override fun onRefresh() {
        viewModel.getTickets(getUserID())
    }

    companion object {

        private const val EXTRAS_USER_ID = "EXTRAS_USER_ID"
        private const val EXTRAS_DIFFERENT_USER = "EXTRAS_DIFFERENT_USER"

        fun createIntent(context: Context, userId: String, isDifferentUser: Boolean = false): Intent {
            return Intent(context, TicketsActivity::class.java).apply {
                putExtra(EXTRAS_USER_ID, userId)
                putExtra(EXTRAS_DIFFERENT_USER, isDifferentUser)
            }
        }
    }
}