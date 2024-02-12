package com.alparslanturk.kombineapp.ui.home.createticket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.application.SessionManager.getUserID
import com.alparslanturk.kombineapp.custom.selectionbottomsheet.SelectionBottomSheetDialog
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.data.entities.models.SelectionDialogItem
import com.alparslanturk.kombineapp.databinding.ActivityCreateTicketBinding
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.utils.hideKeyboard
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateTicketActivity : BaseActivity() {

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
                    SelectionBottomSheetDialog(
                        list = listOf(),
                        onItemSelected = { _ ->
                            //viewModel.updateSelectedRelation(item.name)
                            //this.setText(item.name)
                        }
                    ).show(supportFragmentManager, "RelationsDialog")
                }
            }
            edtSelectTariff.apply {
                setOnClickListener {
                    hideKeyboard(it)
                    SelectionBottomSheetDialog(
                        list = viewModel.getTariffList(),
                        onItemSelected = { item ->
                            viewModel.updateSelectedTariffID(item.id)
                            this.setText(item.name)
                        }
                    ).show(supportFragmentManager, "RelationsDialog")
                }
            }

            btnCreateTicket.setOnClickListener {
                viewModel.createTicket(
                    CreateTicketRequest(
                        matchId = "740a78df-0d85-463c-0bce-08dc2bc04a1c",
                        tariffId = viewModel.getSelectedTariffID(),
                        userId = getUserID(),
                        tribune = edtTribune.text.toString(),
                        block = edtBlock.text.toString(),
                        order = edtOrder.text.toString(),
                        price = edtPrice.text.toString().toInt(),
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
                                        tariffCategoryList.forEach { category ->
                                            category.tariffList.forEach { tariff ->
                                                viewModel.getTariffList().add(SelectionDialogItem(name = tariff.name, id = tariff.id))
                                            }
                                        }
                                    }
                                }
                            }
                            is Result.Auth -> {}
                        }
                    }
                }

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

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, CreateTicketActivity::class.java)
        }
    }
}
