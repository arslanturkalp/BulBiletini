package com.alparslanturk.biletdevret.ui.messages.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.data.entities.enums.ChatMessageType
import com.alparslanturk.biletdevret.data.entities.enums.DateFormatType
import com.alparslanturk.biletdevret.data.entities.models.Message
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.TicketUser
import com.alparslanturk.biletdevret.databinding.ActivityChatBinding
import com.alparslanturk.biletdevret.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.biletdevret.ui.adapters.ChatAdapter
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.utils.addOnBackPressedListener
import com.alparslanturk.biletdevret.utils.getDataExtra
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import com.alparslanturk.biletdevret.utils.toDate
import com.alparslanturk.biletdevret.utils.toString
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
        setupSwipeRefresh()
        setupRecyclerView()
        setupUI()
        setupObservers()

        viewModel.retrieveMessages(user.id)
    }

    private fun setupToolbar() {
        addOnBackPressedListener { onBackClicked() }
        binding.toolbar.apply {
            setTitle(user.username)
            setBackButton { onBackClicked() }
        }
    }

    private fun setupSwipeRefresh() = binding.swipeRefreshLayout.setOnRefreshListener(this)

    private fun setupRecyclerView() {
        binding.rvMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupUI() {
        binding.apply {
            edtMessage.setOnFocusChangeListener { _, hasFocus ->
                rvMessages.postDelayed({
                    if (hasFocus) rvMessages.smoothScrollToPosition(chatAdapter.itemCount)
                }, 200)
            }
            btnSend.setOnClickListener {
                if (!edtMessage.text.isNullOrEmpty()) {
                    viewModel.sendMessage(
                        SendMessageRequest(
                            fromUserId = getUserID(),
                            toUserId = user.id,
                            message = edtMessage.text.toString()
                        )
                    )
                }
                edtMessage.setText("")
            }
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
                            is Result.Loading -> {
                                showProgress()
                            }
                            is Result.Success -> {
                                if (it.code == 300) {
                                    dismissProgress()
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                                } else {
                                    dismissProgress()
                                    generateList(it.body?.data?.userMessageList.orEmpty())
                                    isAnyUpdate = true
                                    binding.rvMessages.smoothScrollToPosition(chatAdapter.itemCount)
                                }
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
                                it.body?.apply {
                                    if (code == 200) {
                                        dismissProgress()
                                        isAnyUpdate = true
                                        viewModel.retrieveMessages(user.id)
                                    } else {
                                        dismissProgress()
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

    private fun generateList(messages: List<Message>) {
        val list: ArrayList<Message> = arrayListOf()

        messages.forEach {
            if (it.fromUserId == getUserID()) {
                list.add(Message(it.fromUserId, it.toUserId, it.message, it.wasSeen, it.createdDate, ChatMessageType.SENT, it.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)!!.toString(DateFormatType.DATE_WITH_SPACES_SHORT)))
            } else {
                list.add(Message(it.fromUserId, it.toUserId, it.message, it.wasSeen, it.createdDate, ChatMessageType.RECEIVED, it.createdDate.toDate(dateFormatType = DateFormatType.DATE_TIME)!!.toString(DateFormatType.DATE_WITH_SPACES_SHORT)))
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