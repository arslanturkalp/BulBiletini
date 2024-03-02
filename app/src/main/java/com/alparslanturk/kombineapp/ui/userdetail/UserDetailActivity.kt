package com.alparslanturk.kombineapp.ui.userdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.TicketUser
import com.alparslanturk.kombineapp.databinding.ActivityUserDetailBinding
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.messages.chat.ChatActivity
import com.alparslanturk.kombineapp.ui.settings.tickets.TicketsActivity
import com.alparslanturk.kombineapp.ui.userdetail.usercomments.UserCommentsActivity
import com.alparslanturk.kombineapp.utils.getDataExtra
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UserDetailViewModel>()

    private lateinit var user: TicketUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = intent.getDataExtra(EXTRAS_USER)

        setupUI()
        setupObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        with(binding) {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            tvUserName.text = "${user.name} ${user.surname}"
            ratingBar.rating = (user.rating ?: 0.0).toFloat()
            llCall.setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL).also { it.data = Uri.parse("tel:+90" + user.phoneNumber) }) }
            llMessage.setOnClickListener { navigateToChat() }
            llAdverts.setOnClickListener { navigateToUserTickets() }
            llComments.setOnClickListener { navigateToUserComments() }
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
                                        binding.tvTotalTicket.text = this.profileCommentList.size.toString()
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

    private fun navigateToChat() = startActivity(ChatActivity.createIntent(this, user))

    private fun navigateToUserTickets() = startActivity(TicketsActivity.createIntent(this, user.id, true))

    private fun navigateToUserComments() = startActivity(UserCommentsActivity.createIntent(this))

    companion object {

        private const val EXTRAS_USER = "EXTRAS_USER"

        fun createIntent(context: Context, user: TicketUser): Intent {
            return Intent(context, UserDetailActivity::class.java).apply {
                putExtra(EXTRAS_USER, user)
            }
        }
    }
}