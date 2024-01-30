package com.alparslanturk.kombineapp.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.kombineapp.R
import com.alparslanturk.kombineapp.data.entities.enums.DateFormatType
import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.databinding.ActivityRegisterBinding
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.ui.base.BaseActivity
import com.alparslanturk.kombineapp.ui.generic.GenericDatePickerDialog
import com.alparslanturk.kombineapp.ui.login.LoginActivity
import com.alparslanturk.kombineapp.utils.hideKeyboard
import com.alparslanturk.kombineapp.utils.showAlertDialogTheme
import com.alparslanturk.kombineapp.utils.toString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            edtDate.apply {
                setText(Date().toString(DateFormatType.DATE_TIME_WITH_DOT))
                setOnClickListener {
                    hideKeyboard(it)
                    GenericDatePickerDialog(this, isTimeRequired = true).show(supportFragmentManager, "DatePickerDialog")
                }
            }
            btnRegister.setOnClickListener {
                setupObservers()
                viewModel.register(
                    RegisterRequest(
                        name = edtName.text.toString(),
                        surname = edtSurname.text.toString(),
                        username = edtUserName.text.toString(),
                        password = edtPassword.text.toString(),
                        email = edtMail.text.toString(),
                        dateOfBirth = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(SimpleDateFormat("dd.MM.yyyy").parse(edtDate.text.toString())!!),
                        phoneNumber = edtPhone.text.toString(),
                        canCall = true,
                        isShownPhoneNumber = true
                    )
                )
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                registerFlow.collect {
                    when (it) {
                        is Result.Error -> {
                            showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                        }
                        is Result.Loading -> {}
                        is Result.Success -> {
                            if (it.code == 300) {
                                showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.message)
                            } else {
                                navigateToLogin()
                            }
                        }
                        is Result.Auth -> {}
                    }
                }
            }
        }
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(this@RegisterActivity))

    companion object {
        fun createIntent(context: Context?): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }
}