package com.alparslanturk.biletdevret.ui.settings.updateuser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.application.SessionManager.getUserID
import com.alparslanturk.biletdevret.custom.selectionbottomsheet.SelectionBottomSheetDialog
import com.alparslanturk.biletdevret.data.entities.enums.ChangeProfilePhotoActionType
import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.databinding.ActivityUpdateUserInfoBinding
import com.alparslanturk.biletdevret.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.biletdevret.ui.base.BaseActivity
import com.alparslanturk.biletdevret.utils.FileUtils.Companion.convertToBase64
import com.alparslanturk.biletdevret.utils.addOnBackPressedListener
import com.alparslanturk.biletdevret.utils.show
import com.alparslanturk.biletdevret.utils.showAlertDialogTheme
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateUserInfoActivity : BaseActivity() {

    private val binding by lazy { ActivityUpdateUserInfoBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<UpdateUserInfoViewModel>()

    private var isAnyUpdate: Boolean = false

    private fun onBackClicked() = when (isAnyUpdate) {
        true -> returnResult()
        false -> finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupUI()
        setupObservers()

        viewModel.getUserDetail(getUserID())
    }

    private fun setupToolbar() {
        addOnBackPressedListener { onBackClicked() }
        binding.toolbar.apply {
            setTitle(getString(R.string.update_account_info))
            setBackButton { onBackClicked() }
        }
    }

    private fun setupUI() {
        with(binding) {
            ivProfile.setOnClickListener { showOptionDialog() }
            ivEditProfilePhoto.setOnClickListener { showOptionDialog() }

            btnUpdateUserInfo.setOnClickListener {
                viewModel.requestUpdateUserInfo(
                    UpdateUserInfoRequest(
                        userId = getUserID(),
                        newProfilePhoto = if (viewModel.selectedProfilePhoto?.path.isNullOrEmpty()) "" else convertToBase64(viewModel.selectedProfilePhoto?.path.orEmpty()),
                        newName = edtNewName.text.toString(),
                        newSurname = edtNewSurname.text.toString(),
                        newPhoneNumber = edtNewPhone.text.toString(),
                        newIsShownPhoneNumber = true
                    )
                )
            }
        }
    }


    private fun showOptionDialog() {
        SelectionBottomSheetDialog(
            title = getString(R.string.change_profile),
            list = viewModel.getOptionList(),
            onItemSelected = {
                when (it.id) {
                    ChangeProfilePhotoActionType.CAMERA.id -> openCamera()
                    ChangeProfilePhotoActionType.GALLERY.id -> openGallery()
                }
            }
        ).show(this)
    }

    private fun openCamera() {
        ImagePicker.with(this)
            .cameraOnly()
            .crop()
            .compress(128)
            .createIntent { resultPhoto.launch(it) }
    }

    private fun openGallery() {
        ImagePicker.with(this)
            .galleryOnly()
            .crop()
            .compress(128)
            .createIntent { resultPhoto.launch(it) }
    }

    private val resultPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> updateProfilePhoto(data?.data!!)
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateProfilePhoto(uri: Uri) {
        with(viewModel) {
            updateProfilePhoto(uri)
            binding.ivProfile.setImageURI(uri)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    updateUserInfoFlow.collect {
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
                                        Toast.makeText(this@UpdateUserInfoActivity, getString(R.string.successfully_updated_account_info), Toast.LENGTH_LONG).show()
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = message)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    getUserDetailFlow.collect {
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
                                        with(binding) {
                                            data.apply {
                                                ivProfile.apply {
                                                    Glide.with(context)
                                                        .asBitmap()
                                                        .load(Base64.decode(profilePhoto.orEmpty(), Base64.DEFAULT))
                                                        .error(R.color.black)
                                                        .into(this)
                                                }
                                                edtNewName.setText(name)
                                                edtNewSurname.setText(surname)
                                                edtNewPhone.setText(phoneNumber)
                                            }
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

    private fun returnResult() {
        setResult(RESULT_OK)
        finish()
    }

    companion object {

        fun createIntent(context: Context?): Intent {
            return Intent(context, UpdateUserInfoActivity::class.java)
        }
    }
}