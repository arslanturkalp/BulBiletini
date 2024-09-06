package com.alparslanturk.bulbiletini.ui.userdetail.usercomments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.ActivityCommentsBinding
import com.alparslanturk.bulbiletini.ui.adapters.CommentsAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
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

        val userID = intent.getDataExtra<String>(EXTRAS_DATA_USER_ID)

        viewModel.getComments(userID)
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("User Comments Page", UserCommentsActivity::class.java.name)
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
                                it.body?.apply {
                                    if (code == 200) {
                                        data.apply {
                                            commentsAdapter.updateAdapter(this.profileCommentList)
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

    companion object {

        private const val EXTRAS_DATA_USER_ID = "EXTRAS_DATA_USER_ID"

        fun createIntent(context: Context, userID: String): Intent {
            return Intent(context, UserCommentsActivity::class.java).apply {
                putExtra(EXTRAS_DATA_USER_ID, userID)
            }
        }
    }
}