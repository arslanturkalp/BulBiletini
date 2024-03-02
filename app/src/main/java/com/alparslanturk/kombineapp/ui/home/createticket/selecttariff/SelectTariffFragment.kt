package com.alparslanturk.kombineapp.ui.home.createticket.selecttariff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.TariffCategoryList
import com.alparslanturk.kombineapp.databinding.FragmentSelectTariffBinding
import com.alparslanturk.kombineapp.ui.base.BaseBottomSheetDialogFragment
import com.alparslanturk.kombineapp.ui.home.createticket.CreateTicketActivity.Companion.EXTRAS_DATA_SELECTED_TARIFF
import com.alparslanturk.kombineapp.utils.listener.DialogCloseListener
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectTariffFragment: BaseBottomSheetDialogFragment() {

    private val binding by lazy { FragmentSelectTariffBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SelectTariffViewModel>()

    private val selectTariffAdapter by lazy { SelectTariffAdapter { returnResult(it) } }

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

        viewModel.requestGetTariffList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.select_tariff))
            setDownButton { dismiss() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTariffs.apply {
            adapter = selectTariffAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getTariffListFlow.collect {
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
                                        selectTariffAdapter.updateAdapter(this.tariffCategoryList)
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

    private fun returnResult(tariff: TariffCategoryList) {
        dismiss()
        closeListener?.dialogClosed(this, bundleOf(EXTRAS_DATA_SELECTED_TARIFF to tariff))
    }

    companion object {

        val TAG: String? = this::class.java.canonicalName

        fun newInstance(closeListener: DialogCloseListener): SelectTariffFragment =
            SelectTariffFragment().apply {
                this.closeListener = closeListener
            }
    }
}