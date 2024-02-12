package com.alparslanturk.kombineapp.ui.settings.tickets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.Ticket
import com.alparslanturk.kombineapp.databinding.ActivityTicketsBinding
import com.alparslanturk.kombineapp.ui.adapters.TicketsAdapter
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.kombineapp.utils.setGone
import com.alparslanturk.kombineapp.utils.setVisible
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TicketsActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { ActivityTicketsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TicketsViewModel>()

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerView()
        setupObservers()

        viewModel.getTickets(getUserID())
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.adverts))
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
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgress()
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        ticketsAdapter.updateAdapter(ticketList)
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

        fun createIntent(context: Context): Intent {
            return Intent(context, TicketsActivity::class.java)
        }
    }
}