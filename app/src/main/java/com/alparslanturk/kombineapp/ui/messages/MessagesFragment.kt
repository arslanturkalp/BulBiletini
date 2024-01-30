package com.alparslanturk.kombineapp.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.databinding.FragmentMessagesBinding
import com.alparslanturk.kombineapp.ui.base.BaseFragment

class MessagesFragment : BaseFragment() {

    private val binding by lazy { FragmentMessagesBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.messages))
        }
    }
}