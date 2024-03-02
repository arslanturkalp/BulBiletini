package com.alparslanturk.kombineapp.ui.messages

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.ContactList
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.FragmentMessagesBinding
import com.alparslanturk.kombineapp.ui.adapters.MessagesAdapter
import com.alparslanturk.kombineapp.ui.base.BaseFragment
import com.alparslanturk.kombineapp.ui.main.MainActivity
import com.alparslanturk.kombineapp.ui.messages.chat.ChatActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessagesFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { FragmentMessagesBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MessagesViewModel>()

    private val messagesAdapter by lazy { MessagesAdapter { navigateToChat(it) } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerView()
        setupObservers()

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backToMainMenu()
                }
            })

        viewModel.getUserMessages(getUserID())
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.messages))
        }
    }

    private fun setupSwipeRefresh() = binding.swipeRefreshLayout.setOnRefreshListener(this)

    private fun setupRecyclerView() {
        binding.rvMessages.apply {
            adapter = messagesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                getUserMessagesFlow.collect {
                    when (it) {
                        is Result.Error -> {
                            dismissProgress()
                            showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                        }
                        is Result.Loading -> showProgress()
                        is Result.Success -> {
                            if (it.code == 300) {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            } else {
                                dismissProgress()
                                it.body?.data?.apply {
                                    messagesAdapter.updateAdapter(contactList)
                                    wasNotSeenTotalMessageCount.apply { if (this != 0) (activity as MainActivity).setNotificationBadge(this) }
                                }
                            }
                        }
                        is Result.Auth -> {}
                    }
                }
            }
        }
    }

    private fun navigateToChat(contactList: ContactList) = resultRefreshMessages.launch(ChatActivity.createIntent(context, contactList.user))

    private val resultRefreshMessages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getUserMessages(getUserID())
        }
    }

    private fun showProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun backToMainMenu() {
        (activity as MainActivity).apply {
            showFragment(homeFragment)
            setItemInNavigation(homeFragment)
        }
    }

    override fun onRefresh() {
        viewModel.getUserMessages(getUserID())
    }
}