package com.alparslanturk.biletdevret.ui.matchdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.models.Match
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.Ticket
import com.alparslanturk.biletdevret.databinding.ActivityMatchDetailBinding
import com.alparslanturk.biletdevret.ui.adapters.TicketsAdapter
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.ticketdetail.TicketDetailActivity
import com.alparslanturk.biletdevret.utils.getDataExtra
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityMatchDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MatchDetailViewModel>()

    private val ticketsAdapter by lazy { TicketsAdapter { navigateToTicketDetail(it) } }

    private lateinit var match: Match

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        match = intent.getDataExtra("EXTRAS_MATCH")

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.getMatchTickets(match.id)
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle("${match.homeTeamName} - ${match.awayTeamName}")
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvTickets.apply {
                adapter = ticketsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getMatchTicketsFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgressDialog()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgressDialog()
                                it.body?.apply {
                                    if (code == 200) {
                                        data.apply {
                                            ticketsAdapter.updateAdapter(ticketList)
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

    private fun navigateToTicketDetail(ticket: Ticket) = startActivity(TicketDetailActivity.createIntent(this, ticket))

    companion object {

        private const val EXTRAS_MATCH = "EXTRAS_MATCH"

        fun createIntent(context: Context, match: Match): Intent {
            return Intent(context, MatchDetailActivity::class.java).apply {
                putExtra(EXTRAS_MATCH, match)
            }
        }
    }
}