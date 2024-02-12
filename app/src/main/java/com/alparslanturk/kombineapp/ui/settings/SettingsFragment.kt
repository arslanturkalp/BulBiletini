package com.alparslanturk.kombineapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.clearPassword
import com.alparslanturk.kombineapp.application.SessionManager.clearUserID
import com.alparslanturk.kombineapp.application.SessionManager.clearUserName
import com.alparslanturk.kombineapp.application.SessionManager.getUserName
import com.alparslanturk.kombineapp.databinding.FragmentSettingsBinding
import com.alparslanturk.kombineapp.ui.base.BaseFragment
import com.alparslanturk.kombineapp.ui.login.LoginActivity
import com.alparslanturk.kombineapp.ui.main.MainActivity
import com.alparslanturk.kombineapp.ui.settings.tariffs.TariffsActivity
import com.alparslanturk.kombineapp.ui.settings.tickets.TicketsActivity
import com.alparslanturk.kombineapp.ui.userdelete.UserDeleteActivity

class SettingsFragment : BaseFragment() {

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupUI()

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backToMainMenu()
                }
            })
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.settings))
        }
    }

    private fun setupUI() {
        binding.apply {
            tvUserName.text = getUserName()
            llBuyTariff.setOnClickListener { startActivity(TariffsActivity.createIntent(requireContext())) }
            llMyTickets.setOnClickListener { startActivity(TicketsActivity.createIntent(requireContext())) }
            llDeleteAccount.setOnClickListener { startActivity(UserDeleteActivity.createIntent(requireContext())) }
            llSignOut.setOnClickListener {
                clearUserName()
                clearPassword()
                clearUserID()
                navigateToLogin()
            }
        }
    }

    private fun backToMainMenu() {
        (activity as MainActivity).apply {
            showFragment(homeFragment)
            setItemInNavigation(homeFragment)
        }
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(requireContext()))

}