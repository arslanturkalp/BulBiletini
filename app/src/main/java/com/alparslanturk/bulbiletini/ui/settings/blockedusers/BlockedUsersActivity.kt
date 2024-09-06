package com.alparslanturk.bulbiletini.ui.settings.blockedusers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.TicketUser
import com.alparslanturk.bulbiletini.databinding.ActivityBlockedUsersBinding
import com.alparslanturk.bulbiletini.ui.adapters.BlockedUsersAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.userdetail.UserDetailActivity
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlockedUsersActivity : BaseActivity() {

    private val binding by lazy { ActivityBlockedUsersBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<BlockedUsersViewModel>()

    private val blockedUsersAdapter by lazy { BlockedUsersAdapter { navigateToUserDetail(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.getBlockedUsers(getUserID())
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Blocked Users Page", BlockedUsersActivity::class.java.name)
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.blocked_users))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvBlockedUsers.apply {
            adapter = blockedUsersAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getBlockedUsersFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgressDialog()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgressDialog()
                                blockedUsersAdapter.updateAdapter(it.body?.data?.blockedUserList.orEmpty())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToUserDetail(user: TicketUser) = startActivity(UserDetailActivity.createIntent(this, user, true))

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, BlockedUsersActivity::class.java)
        }
    }
}