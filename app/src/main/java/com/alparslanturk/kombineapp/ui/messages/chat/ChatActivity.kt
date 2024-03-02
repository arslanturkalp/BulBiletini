package com.alparslanturk.kombineapp.ui.messages.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.enums.ChatMessageType
import com.alparslanturk.kombineapp.data.entities.models.Message
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.TicketUser
import com.alparslanturk.kombineapp.databinding.ActivityChatBinding
import com.alparslanturk.kombineapp.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.kombineapp.ui.adapters.ChatAdapter
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.getDataExtra
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<ChatViewModel>()

    private val chatAdapter by lazy { ChatAdapter() }

    private var isAnyUpdate: Boolean = false

    lateinit var user: TicketUser

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = intent.getDataExtra(EXTRAS_DATA_SELECTED_USER)

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.retrieveMessages(user.id)
        binding.apply {
            btnSend.setOnClickListener {
                viewModel.sendMessage(
                    SendMessageRequest(
                        fromUserId = getUserID(),
                        toUserId = user.id,
                        message = edtMessage.text.toString()
                    )
                )
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(user.name)
            setBackButton { onBackClicked() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                viewModel.apply {
                    retrieveMessagesFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                if (it.code == 300) {
                                    dismissProgress()
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                                } else {
                                    dismissProgress()
                                    generateList(it.body?.data?.userMessageList.orEmpty())
                                }
                            }
                            is Result.Auth -> {
                                dismissProgress()
                            }
                        }
                    }
                }
            }
            launch {
                viewModel.apply {
                    sendMessageFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}
                            is Result.Success -> {
                                if (it.code == 300) {
                                    dismissProgress()
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                                } else {
                                    dismissProgress()
                                    isAnyUpdate = true
                                    viewModel.retrieveMessages(user.id)
                                }
                            }
                            is Result.Auth -> {
                                dismissProgress()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun generateList(messages: List<Message>) {
        val list: ArrayList<Message> = arrayListOf()

        messages.forEach {
            if (it.fromUserId == getUserID()) {
                list.add(Message(it.fromUserId, it.toUserId, it.message, it.wasSeen, it.createdDate, ChatMessageType.SENT))
            } else {
                list.add(Message(it.fromUserId, it.toUserId, it.message, it.wasSeen, it.createdDate, ChatMessageType.RECEIVED))
            }
        }
        chatAdapter.updateAdapter(list)
    }

    private fun showProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        private const val EXTRAS_DATA_SELECTED_USER = "EXTRAS_DATA_SELECTED_USER"

        fun createIntent(context: Context?, user: TicketUser): Intent {
            return Intent(context, ChatActivity::class.java).apply {
                putExtra(EXTRAS_DATA_SELECTED_USER, user)
            }
        }
    }

    override fun onRefresh() {
        viewModel.retrieveMessages(user.id)
    }
}