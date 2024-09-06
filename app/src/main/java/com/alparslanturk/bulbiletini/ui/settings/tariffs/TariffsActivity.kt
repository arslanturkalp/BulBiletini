package com.alparslanturk.bulbiletini.ui.settings.tariffs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.data.entities.models.Tariff
import com.alparslanturk.bulbiletini.databinding.ActivityTariffsBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.bulbiletini.ui.adapters.TariffCategoriesAdapter
import com.alparslanturk.bulbiletini.ui.base.BaseActivity
import com.alparslanturk.bulbiletini.utils.addOnBackPressedListener
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TariffsActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private val binding by lazy { ActivityTariffsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TariffsViewModel>()

    private val tariffCategoriesAdapter by lazy { TariffCategoriesAdapter { showRequireDialog(it) } }

    private lateinit var billingClient: BillingClient

    private lateinit var purchaseUpdateListener: PurchasesUpdatedListener

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

        purchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (pur in purchases) {
                    handlePurchase(pur)
                }
            }
        }

        billingClient = BillingClient.newBuilder(this)
            .setListener(purchaseUpdateListener)
            .enablePendingPurchases()
            .build()

        viewModel.getTariffList()
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Tariffs Page", TariffsActivity::class.java.name)
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
                                        showAlertDialogTheme(title = getString(R.string.tariff_buy_success), contentMessage = message)
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

    private fun showRequireDialog(tariff: Tariff) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    receiveTariff(tariff)
                }
            }

            override fun onBillingServiceDisconnected() {}
        })
    }

    private fun handlePurchase(purchase: Purchase) {
        if (!purchase.isAcknowledged) {
            billingClient.acknowledgePurchase(
                AcknowledgePurchaseParams
                    .newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
            ) {

                if (it.responseCode == BillingClient.BillingResponseCode.OK) {
                    for (pur in purchase.products) {
                        consumePurchase(purchase, pur)
                    }
                }
            }
        }
    }

    private fun consumePurchase(purchase: Purchase, tariffID: String) {
        val params = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.consumeAsync(params) { _, _ ->
            purchaseTariff(tariffID)
        }
    }

    private fun receiveTariff(tariff: Tariff) {
        val productList = ArrayList<QueryProductDetailsParams.Product>()

        productList.add(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(tariff.marketId.orEmpty())
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { _, list ->
            launchPurchaseFlow(list[0])
        }
    }

    private fun launchPurchaseFlow(productDetails: ProductDetails) {
        val productList = ArrayList<BillingFlowParams.ProductDetailsParams>()
        productList.add(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder().setProductDetailsParamsList(productList).build()
        billingClient.launchBillingFlow(this, billingFlowParams)
    }

    private fun purchaseTariff(tariffID: String) = viewModel.purchaseTariff(PurchaseTariffRequest(getUserID(), tariffID))

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