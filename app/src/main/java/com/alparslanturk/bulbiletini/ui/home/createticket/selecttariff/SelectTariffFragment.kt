package com.alparslanturk.bulbiletini.ui.home.createticket.selecttariff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.MyTariff
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.FragmentSelectTariffBinding
import com.alparslanturk.bulbiletini.ui.adapters.MyTariffsAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseBottomSheetDialogFragment
import com.alparslanturk.bulbiletini.ui.home.createticket.CreateTicketActivity.Companion.EXTRAS_DATA_SELECTED_TARIFF
import com.alparslanturk.bulbiletini.ui.settings.tariffs.TariffsActivity
import com.alparslanturk.bulbiletini.utils.listener.DialogCloseListener
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectTariffFragment : BaseBottomSheetDialogFragment() {

    private val binding by lazy { FragmentSelectTariffBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SelectTariffViewModel>()

    private val myTariffsAdapter by lazy { MyTariffsAdapter { returnResult(it) } }

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
        setupUI()

        viewModel.requestGetMyTariffs(getUserID())
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Select Tariff Page", SelectTariffFragment::class.java.name)
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.select_tariff))
            setDownButton { dismiss() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTariffs.apply {
            adapter = myTariffsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getMyTariffsFlow.collect {
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
                                            myTariffsAdapter.updateAdapter(this.purchaseFlowList)
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

    private fun setupUI() {
        with(binding) {
            btnBuyTariff.setOnClickListener { navigateToBuyTicket() }
        }
    }

    private fun navigateToBuyTicket() = resultMyTariffsList.launch(TariffsActivity.createIntent(requireContext()))

    private val resultMyTariffsList = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            viewModel.requestGetMyTariffs(getUserID())
        }
    }

    private fun returnResult(tariff: MyTariff) {
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