package com.alparslanturk.bulbiletini.ui.userdetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.TicketUser
import com.alparslanturk.bulbiletini.databinding.ActivityUserDetailBinding
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.ui.messages.chat.ChatActivity
import com.alparslanturk.bulbiletini.ui.settings.tickets.TicketsActivity
import com.alparslanturk.bulbiletini.ui.userdetail.addcomment.AddCommentActivity
import com.alparslanturk.bulbiletini.ui.userdetail.rateuser.RateUserActivity
import com.alparslanturk.bulbiletini.ui.userdetail.usercomments.UserCommentsActivity
import com.alparslanturk.bulbiletini.utils.getDataExtra
import com.alparslanturk.bulbiletini.utils.setGone
import com.alparslanturk.bulbiletini.utils.setVisible
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailActivity : BaseActivity() {

    private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UserDetailViewModel>()

    private lateinit var user: TicketUser
    private var isBlockedUser: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        user = intent.getDataExtra(EXTRAS_USER)
        isBlockedUser = intent.getDataExtra(EXTRAS_IS_BLOCKED_USER)

        setupUI()
        setupObservers()

        viewModel.getComments(user.id)
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("User Detail Page", UserDetailActivity::class.java.name)
    }

    @SuppressLint("SetTextI18n")
    private fun setupUI() {
        with(binding) {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            ivProfilePhoto.apply {
                Glide.with(context)
                    .asBitmap()
                    .load(Base64.decode(user.profilePhoto.orEmpty(), Base64.DEFAULT))
                    .error(R.color.black)
                    .into(this)
            }
            tvUserName.text = "${user.name} ${user.surname}"
            ratingBar.rating = (user.rating ?: 0.0).toFloat()
            llCall.apply {
                setOnClickListener { startActivity(Intent(Intent.ACTION_DIAL).also { it.data = Uri.parse("tel:+90" + user.phoneNumber) }) }
                isEnabled = !isBlockedUser
                alpha = if (isBlockedUser) 0.5f else 1f
            }
            llMessage.apply {
                setOnClickListener { navigateToChat() }
                isEnabled = !isBlockedUser
                alpha = if (isBlockedUser) 0.5f else 1f
            }
            llWhatsapp.apply {
                setOnClickListener { sendWhatsappMessage("90" + user.phoneNumber) }
                if (isBlockedUser) setGone() else setVisible()
            }
            llAdverts.setOnClickListener { navigateToUserTickets() }
            llComments.setOnClickListener { navigateToUserComments() }
            llAddComment.setOnClickListener { navigateToAddComment() }
            llRateUser.setOnClickListener { navigateToRateUser() }
            llBlockUser.setOnClickListener { showBlockUserConfirmDialog() }
            tvBlockUser.text = if (isBlockedUser) getString(R.string.unblock_user) else getString(R.string.block_user)

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
                                viewModel.getUserDetail(user.id)
                                it.body?.apply {
                                    if (code == 300 && message != getString(R.string.not_comment_in_profile)) {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                    } else if (message == getString(R.string.not_comment_in_profile)) {
                                        binding.tvTotalTicket.text = "0"
                                    } else {
                                        data.apply {
                                            binding.tvTotalTicket.text = this.profileCommentList.size.toString()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    blockUserFlow.collect {
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
                                        Toast.makeText(this@UserDetailActivity, getString(R.string.user_blocked), Toast.LENGTH_SHORT).show()
                                        binding.tvBlockUser.text = getString(R.string.unblock_user)
                                        isBlockedUser = true
                                        setupUI()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    unBlockUserFlow.collect {
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
                                        Toast.makeText(this@UserDetailActivity, getString(R.string.user_blocked_remove), Toast.LENGTH_SHORT).show()
                                        binding.tvBlockUser.text = getString(R.string.block_user)
                                        isBlockedUser = false
                                        setupUI()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    getUserDetailFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                it.body?.apply {
                                    if (code == 200) {
                                        with(binding) {
                                            ratingBar.rating = (data.rating ?: 0.0).toFloat()
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

    private fun navigateToChat() = startActivity(ChatActivity.createIntent(this, user))

    private fun sendWhatsappMessage(toNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val text = getString(R.string.message_template)
        intent.apply {
            setData(Uri.parse("http://api.whatsapp.com/send?phone=$toNumber&text=$text"))
            setPackage("com.whatsapp")
        }
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.please_install_whatsapp), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showBlockUserConfirmDialog() {
        showAlertDialogTheme(
            title = if (isBlockedUser) getString(R.string.unblock_user) else getString(R.string.block_user),
            contentMessage = if (isBlockedUser) getString(R.string.are_you_sure_want_to_unblock_user) else getString(R.string.are_you_sure_want_to_block_user),
            showNegativeButton = true,
            positiveButtonTitle = getString(R.string.no),
            negativeButtonTitle = getString(R.string.yes),
            onNegativeButtonClick = { if (isBlockedUser) viewModel.unBlockUser(user.id, getUserID()) else viewModel.blockUser(user.id, getUserID()) }
        )
    }

    private fun navigateToUserTickets() = startActivity(TicketsActivity.createIntent(this, user.id, true))

    private fun navigateToUserComments() = startActivity(UserCommentsActivity.createIntent(this, user.id))

    private fun navigateToAddComment() = addCommentResult.launch(AddCommentActivity.createIntent(this, user.id))

    private fun navigateToRateUser() = startActivity(RateUserActivity.createIntent(this, user.id))

    private val addCommentResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.getComments(user.id)
        }
    }

    companion object {

        private const val EXTRAS_USER = "EXTRAS_USER"
        private const val EXTRAS_IS_BLOCKED_USER = "EXTRAS_IS_BLOCKED_USER"

        fun createIntent(context: Context, user: TicketUser, isBlockedUser: Boolean): Intent {
            return Intent(context, UserDetailActivity::class.java).apply {
                putExtra(EXTRAS_USER, user)
                putExtra(EXTRAS_IS_BLOCKED_USER, isBlockedUser)
            }
        }
    }
}