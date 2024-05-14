package com.alparslanturk.bulbiletini.ui.teams

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.Club
import com.alparslanturk.bulbiletini.databinding.ActivityTeamsBinding
import com.alparslanturk.bulbiletini.ui.adapters.TeamsAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.teamdetail.TeamDetailActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsActivity : BaseActivity() {

    private val binding by lazy { ActivityTeamsBinding.inflate(layoutInflater) }

    private val teamsAdapter by lazy { TeamsAdapter { navigateToTeamDetail(it) } }

    private var clubList: ArrayList<Club> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        clubList = intent.getDataExtra("EXTRAS_TEAM_LIST")

        setupToolbar()
        setupRecyclerView()
        setupTeamsList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.teams))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTeams.apply {
            adapter = teamsAdapter
        }
    }

    private fun setupTeamsList() {
        teamsAdapter.updateAdapter(clubList.sortedByDescending { it.totalTicketCount })
    }

    private fun navigateToTeamDetail(team: Club) = startActivity(TeamDetailActivity.createIntent(this, team))

    companion object {

        private const val EXTRAS_TEAM_LIST = "EXTRAS_TEAM_LIST"

        fun createIntent(context: Context, clubList: ArrayList<Club>): Intent {
            return Intent(context, TeamsActivity::class.java).apply {
                putExtra(EXTRAS_TEAM_LIST, clubList)
            }
        }
    }
}