package com.alparslanturk.kombineapp.ui.teams

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Team
import com.alparslanturk.kombineapp.databinding.ActivityTeamsBinding
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.teamdetail.TeamDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsActivity : BaseActivity() {

    private val binding by lazy { ActivityTeamsBinding.inflate(layoutInflater) }

    private val teamsAdapter by lazy { TeamsAdapter { navigateToTeamDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupTeamsList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.teams))
            setBackButton { onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTeams.apply {
            adapter = teamsAdapter
        }
    }

    private fun setupTeamsList() {
        val list = listOf(
            Team("1", "Fenerbahçe", "#FFED00", "#163962", "https://seeklogo.com/images/F/fenerbahce-spor-kulubu-5-yildizli-arma-logo-64F337AD4A-seeklogo.com.png", "220"),
            Team("2", "Galatasaray", "#FDB912", "#A90432", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Galatasaray_Star_Logo.png/800px-Galatasaray_Star_Logo.png", "360"),
            Team("3", "Beşiktaş", "#000000", "#FFFFFF", "https://upload.wikimedia.org/wikipedia/commons/0/08/Be%C5%9Fikta%C5%9F_Logo_Be%C5%9Fikta%C5%9F_Amblem_Be%C5%9Fikta%C5%9F_Arma.png", "100"),
            Team("4", "Trabzonspor", "#A41D34", "#14C0F1", "https://upload.wikimedia.org/wikipedia/tr/archive/a/ab/20220929150220%21TrabzonsporAmblemi.png", "25"),
            Team("5", "Başakşehir", "#1E3A58", "#ED5E27", "https://upload.wikimedia.org/wikipedia/tr/c/cd/%C4%B0stanbul_Ba%C5%9Fak%C5%9Fehir_FK.png", "4")
        )
        teamsAdapter.updateAdapter(list)
    }

    private fun navigateToTeamDetail(team: Team) = startActivity(TeamDetailActivity.createIntent(this, team))

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, TeamsActivity::class.java)
    }
}