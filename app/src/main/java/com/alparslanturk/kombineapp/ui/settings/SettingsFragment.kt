package com.alparslanturk.kombineapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.clearUserName
import com.alparslanturk.kombineapp.application.SessionManager.getUserName
import com.alparslanturk.kombineapp.databinding.FragmentSettingsBinding
import com.alparslanturk.kombineapp.ui.base.BaseFragment
import com.alparslanturk.kombineapp.ui.login.LoginActivity
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
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.settings))
        }
    }

    private fun setupUI() {
        binding.apply {
            tvUserName.text = getUserName()
            llDeleteAccount.setOnClickListener { startActivity(UserDeleteActivity.createIntent(requireContext())) }
            llSignOut.setOnClickListener {
                clearUserName()
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(requireContext()))

}