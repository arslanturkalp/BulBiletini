package com.alparslanturk.biletdevret.ui.settings.tariffs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.Tariff
import com.alparslanturk.biletdevret.databinding.ActivityTariffsBinding
import com.alparslanturk.biletdevret.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.biletdevret.ui.adapters.TariffCategoriesAdapter
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.utils.addOnBackPressedListener
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TariffsActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { ActivityTariffsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TariffsViewModel>()

    private val tariffCategoriesAdapter by lazy { TariffCategoriesAdapter { purchaseTariff(it) } }

    private var isAnyUpdate: Boolean = false

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()

        viewModel.getTariffList()
    }

    private fun setupToolbar() {
        addOnBackPressedListener { onBackClicked() }
        binding.toolbar.apply {
            setTitle(getString(R.string.tariffs))
            setBackButton { onBackClicked() }
        }
    }

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
                                dismissProgressDialog()
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            }
                            is Result.Loading -> {
                                showProgressDialog()
                            }

                            is Result.Success -> {
                                dismissProgressDialog()
                                it.body?.apply {
                                    if (code == 200) {
                                        data.apply {
                                            tariffCategoriesAdapter.updateAdapter(this.tariffCategoryList)
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    purchaseTariffFlow.collect {
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
                                        isAnyUpdate = true
                                        showAlertDialogTheme(title = getString(R.string.done), contentMessage = message)
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

    private fun purchaseTariff(tariff: Tariff) = viewModel.purchaseTariff(PurchaseTariffRequest(getUserID(), tariff.id))

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
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