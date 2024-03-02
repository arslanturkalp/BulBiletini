package com.alparslanturk.kombineapp.ui.userdetail.usercomments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityCommentsBinding
import com.alparslanturk.kombineapp.ui.adapters.CommentsAdapter
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserCommentsActivity : BaseActivity() {

    private val binding by lazy { ActivityCommentsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UserCommentsViewModel>()

    private val commentsAdapter by lazy { CommentsAdapter { } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.getComments(getUserID())
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.user_comments))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvComments.apply {
            adapter = commentsAdapter
            layoutManager = LinearLayoutManager(this@UserCommentsActivity)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getCommentsFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgressDialog()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgressDialog()
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        commentsAdapter.updateAdapter(this.profileCommentList)
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

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, UserCommentsActivity::class.java)
        }
    }
}