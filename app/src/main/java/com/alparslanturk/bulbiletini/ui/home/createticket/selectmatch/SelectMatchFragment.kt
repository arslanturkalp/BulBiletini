package com.alparslanturk.bulbiletini.ui.home.createticket.selectmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.TicketMatch
import com.alparslanturk.bulbiletini.databinding.FragmentSelectMatchBinding
import com.alparslanturk.bulbiletini.ui.base.BaseBottomSheetDialogFragment
import com.alparslanturk.bulbiletini.ui.home.createticket.CreateTicketActivity.Companion.EXTRAS_DATA_SELECTED_MATCH
import com.alparslanturk.bulbiletini.utils.listener.DialogCloseListener
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectMatchFragment : BaseBottomSheetDialogFragment() {

    private val binding by lazy { FragmentSelectMatchBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SelectMatchViewModel>()

    private val matchesAdapter by lazy { SelectMatchAdapter { returnResult(it) } }

    private var closeListener: DialogCloseListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.requestGetMatchList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.select_match))
            setDownButton { dismiss() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvMatches.apply {
            adapter = matchesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getMatchListFlow.collect {
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
                                            matchesAdapter.updateAdapter(this.matchList)
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

    private fun returnResult(match: TicketMatch) {
        dismiss()
        closeListener?.dialogClosed(this, bundleOf(EXTRAS_DATA_SELECTED_MATCH to match))
    }

    companion object {

        val TAG: String? = this::class.java.canonicalName

        fun newInstance(closeListener: DialogCloseListener): SelectMatchFragment =
            SelectMatchFragment().apply {
                this.closeListener = closeListener
            }
    }
}