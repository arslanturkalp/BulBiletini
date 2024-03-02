package com.alparslanturk.kombineapp.ui.settings.mytariffs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityMyTariffsBinding
import com.alparslanturk.kombineapp.ui.adapters.MyTariffsAdapter
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.settings.tariffs.TariffsActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyTariffsActivity : BaseActivity() {

    private val binding by lazy { ActivityMyTariffsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MyTariffsViewModel>()

    private val tariffsAdapter by lazy { MyTariffsAdapter { } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.getMyTariffs(getUserID())
        binding.btnBuyTariff.setOnClickListener { navigateToBuyTicket() }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.my_tariffs))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        binding.rvMyTariffs.apply {
            adapter = tariffsAdapter
            layoutManager = LinearLayoutManager(this@MyTariffsActivity)
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
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        tariffsAdapter.updateAdapter(this.purchaseFlowList)
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


    private fun navigateToBuyTicket() = resultMyTariffsList.launch(TariffsActivity.createIntent(this))

    private val resultMyTariffsList = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            viewModel.getMyTariffs(getUserID())
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MyTariffsActivity::class.java)
        }
    }
}