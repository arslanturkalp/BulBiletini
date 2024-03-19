package com.alparslanturk.biletdevret.ui.home.createticket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.data.entities.models.MyTariff
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.data.entities.models.TicketMatch
import com.alparslanturk.biletdevret.databinding.ActivityCreateTicketBinding
import com.alparslanturk.biletdevret.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.ui.home.createticket.selectmatch.SelectMatchFragment
import com.alparslanturk.biletdevret.ui.home.createticket.selecttariff.SelectTariffFragment
import com.alparslanturk.biletdevret.ui.settings.tariffs.TariffsActivity
import com.alparslanturk.biletdevret.utils.getParcelableDataExtra
import com.alparslanturk.biletdevret.utils.hideKeyboard
import com.alparslanturk.biletdevret.utils.listener.DialogCloseListener
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTicketActivity : BaseActivity(), DialogCloseListener {

    private val binding by lazy { ActivityCreateTicketBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<CreateTicketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()
        setupUI()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.create_ticket))
            setBackButton { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupUI() {
        with(binding) {
            edtSelectMatch.apply {
                setOnClickListener {
                    hideKeyboard(it)
                    navigateToSelectMatch()
                }
            }
            edtSelectTariff.apply {
                setOnClickListener {
                    hideKeyboard(it)
                    navigateToSelectTariff()
                }
            }

            btnCreateTicket.setOnClickListener {
                viewModel.requestCreateTicket(
                    CreateTicketRequest(
                        matchId = viewModel.getSelectedMatch()!!.matchId,
                        tariffCategoryId = viewModel.getSelectedTariff()!!.tariffCategoryId,
                        userId = getUserID(),
                        tribune = edtTribune.text.toString(),
                        block = edtBlock.text.toString(),
                        order = edtOrder.text.toString(),
                        price = if (edtPrice.text.toString().isEmpty()) 0 else edtPrice.text.toString().toInt(),
                        description = edtDescription.text.toString(),
                    )
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    createTicketFlow.collect {
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
                                            Toast.makeText(this@CreateTicketActivity, getString(R.string.successfully_created_ticket), Toast.LENGTH_LONG).show()
                                            returnResult()
                                        }
                                    } else {
                                        if (message == "Kullanıcının bu tarife için kullanım hakkı mevcut değildir.") {
                                            showAlertDialogTheme(title = getString(R.string.warning), negativeButtonTitle = getString(R.string.buy_tariff), positiveButtonTitle = getString(R.string.close), showNegativeButton = true, onNegativeButtonClick = { navigateToBuyTariff() }, contentMessage = message)
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
    }

    @SuppressLint("SetTextI18n")
    private fun setMatch(match: TicketMatch?) {
        viewModel.updateSelectedMatch(match)
        binding.edtSelectMatch.setText("${match?.homeName} - ${match?.awayName}")
    }

    private fun setTariff(tariff: MyTariff?) {
        viewModel.updateSelectedTariff(tariff)
        binding.edtSelectTariff.setText(tariff?.tariffCategoryName)
    }

    private fun navigateToSelectMatch() = SelectMatchFragment.newInstance(this).show(supportFragmentManager, SelectMatchFragment.TAG)

    private fun navigateToSelectTariff() = SelectTariffFragment.newInstance(this).show(supportFragmentManager, SelectTariffFragment.TAG)

    private fun navigateToBuyTariff() = startActivity(TariffsActivity.createIntent(this))

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    override fun dialogClosed(fragment: BottomSheetDialogFragment, data: Bundle?) {
        viewModel.apply {
            when (fragment) {
                is SelectMatchFragment -> {
                    setMatch(data?.getParcelableDataExtra(EXTRAS_DATA_SELECTED_MATCH))
                }
                is SelectTariffFragment -> {
                    setTariff(data?.getParcelableDataExtra(EXTRAS_DATA_SELECTED_TARIFF))
                }
            }
        }
    }

    companion object {

        const val EXTRAS_DATA_SELECTED_MATCH = "EXTRAS_DATA_SELECTED_MATCH"
        const val EXTRAS_DATA_SELECTED_TARIFF = "EXTRAS_DATA_SELECTED_TARIFF"

        fun createIntent(context: Context?): Intent {
            return Intent(context, CreateTicketActivity::class.java)
        }
    }
}
