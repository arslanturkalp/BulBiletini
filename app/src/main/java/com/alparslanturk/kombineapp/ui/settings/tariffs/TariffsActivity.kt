package com.alparslanturk.kombineapp.ui.settings.tariffs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.Tariff
import com.alparslanturk.kombineapp.databinding.ActivityTariffsBinding
import com.alparslanturk.kombineapp.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.kombineapp.ui.adapters.TariffCategoriesAdapter
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TariffsActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { ActivityTariffsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TariffsViewModel>()

    private val tariffCategoriesAdapter by lazy { TariffCategoriesAdapter { purchaseTariff(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupSwipeRefresh()
        setupRecyclerView()
        setupObservers()

        viewModel.getTariffList()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.tariffs))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupSwipeRefresh() = binding.swipeRefreshLayout.setOnRefreshListener(this)

    private fun setupRecyclerView() {
        with(binding) {
            rvTariffs.apply {
                adapter = tariffCategoriesAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    getTariffListFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgress()
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        tariffCategoriesAdapter.updateAdapter(this.tariffCategoryList)
                                    }
                                }
                            }
                            is Result.Auth -> {}
                        }
                    }
                }

                launch {
                    purchaseTariffFlow.collect {
                        when (it) {
                            is Result.Error -> {
                                dismissProgress()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {}

                            is Result.Success -> {
                                dismissProgress()
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.apply {
                                        showAlertDialogTheme(title = getString(R.string.done), contentMessage = it.body.message)

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

    private fun purchaseTariff(tariff: Tariff) = viewModel.purchaseTariff(PurchaseTariffRequest(getUserID(), tariff.id))

    private fun showProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun dismissProgress() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        viewModel.getTariffList()
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, TariffsActivity::class.java)
        }
    }
}