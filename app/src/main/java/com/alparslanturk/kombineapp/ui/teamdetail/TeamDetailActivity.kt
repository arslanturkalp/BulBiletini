package com.alparslanturk.kombineapp.ui.teamdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alparslanturk.kombineapp.data.entities.models.Team
import com.alparslanturk.kombineapp.databinding.ActivityTeamDetailBinding
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.getExtrazz

class TeamDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityTeamDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        //Alınan team ID ile istek atılacak..
    }

    private fun setupToolbar() {
        val team = intent.getExtrazz<Team>("EXTRAS_TEAM")
        binding.toolbar.apply { setTitle(team.name) }
    }

    companion object {

        private const val EXTRAS_TEAM = "EXTRAS_TEAM"

        fun createIntent(context: Context, team: Team): Intent {
            return Intent(context, TeamDetailActivity::class.java).apply {
                putExtra(EXTRAS_TEAM, team)
            }
        }
    }
}