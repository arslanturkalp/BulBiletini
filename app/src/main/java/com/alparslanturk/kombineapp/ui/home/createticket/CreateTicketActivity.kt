package com.alparslanturk.kombineapp.ui.home.createticket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.TariffCategoryList
import com.alparslanturk.kombineapp.data.entities.models.TicketMatch
import com.alparslanturk.kombineapp.databinding.ActivityCreateTicketBinding
import com.alparslanturk.kombineapp.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.home.createticket.selectmatch.SelectMatchFragment
import com.alparslanturk.kombineapp.ui.home.createticket.selecttariff.SelectTariffFragment
import com.alparslanturk.kombineapp.utils.getParcelableDataExtra
import com.alparslanturk.kombineapp.utils.hideKeyboard
import com.alparslanturk.kombineapp.utils.listener.DialogCloseListener
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
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
                        tariffCategoryId = viewModel.getSelectedTariff()!!.id,
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
                                if (it.body!!.code == 300) {
                                    showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
                                } else {
                                    it.body.data.apply {
                                        Toast.makeText(this@CreateTicketActivity, "Başarıyla bilet oluşturuldu.", Toast.LENGTH_LONG).show()
                                        returnResult()
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

    @SuppressLint("SetTextI18n")
    private fun setMatch(match: TicketMatch?) {
        viewModel.updateSelectedMatch(match)
        binding.edtSelectMatch.setText("${match?.homeName} - ${match?.awayName}")
    }

    private fun setTariff(tariff: TariffCategoryList?) {
        viewModel.updateSelectedTariff(tariff)
        binding.edtSelectTariff.setText(tariff?.name)
    }

    private fun navigateToSelectMatch() = SelectMatchFragment.newInstance(this).show(supportFragmentManager, SelectMatchFragment.TAG)

    private fun navigateToSelectTariff() = SelectTariffFragment.newInstance(this).show(supportFragmentManager, SelectTariffFragment.TAG)

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
