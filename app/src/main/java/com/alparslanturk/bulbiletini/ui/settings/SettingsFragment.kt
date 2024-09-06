package com.alparslanturk.bulbiletini.ui.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alparslanturk.bulbiletini.R
import com.alparslanturk.bulbiletini.application.SessionManager.clearMail
import com.alparslanturk.bulbiletini.application.SessionManager.clearPassword
import com.alparslanturk.bulbiletini.application.SessionManager.clearUserID
import com.alparslanturk.bulbiletini.application.SessionManager.clearUserName
import com.alparslanturk.bulbiletini.application.SessionManager.getUserID
import com.alparslanturk.bulbiletini.application.SessionManager.getUserName
import com.alparslanturk.bulbiletini.custom.selectionbottomsheet.SelectionBottomSheetDialog
import com.alparslanturk.bulbiletini.data.entities.enums.ChangeProfilePhotoActionType
import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.databinding.FragmentSettingsBinding
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.bulbiletini.ui.base.BaseFragment
import com.alparslanturk.bulbiletini.ui.login.LoginActivity
import com.alparslanturk.bulbiletini.ui.main.MainActivity
import com.alparslanturk.bulbiletini.ui.settings.blockedusers.BlockedUsersActivity
import com.alparslanturk.bulbiletini.ui.settings.mycomments.MyCommentsActivity
import com.alparslanturk.bulbiletini.ui.settings.mytariffs.MyTariffsActivity
import com.alparslanturk.bulbiletini.ui.settings.suggestionandcomplaint.SuggestionAndComplaintActivity
import com.alparslanturk.bulbiletini.ui.settings.tariffs.TariffsActivity
import com.alparslanturk.bulbiletini.ui.settings.tickets.TicketsActivity
import com.alparslanturk.bulbiletini.ui.settings.updateuser.UpdateUserInfoActivity
import com.alparslanturk.bulbiletini.ui.settings.userdelete.UserDeleteActivity
import com.alparslanturk.bulbiletini.utils.FileUtils.Companion.convertToBase64
import com.alparslanturk.bulbiletini.utils.show
import com.alparslanturk.bulbiletini.utils.showAlertDialogTheme
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private val binding by lazy { FragmentSettingsBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupObservers()
        setupUI()

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backToMainMenu()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        mFirebaseUtils.logScreenViewEvent("Settings Page", SettingsFragment::class.java.name)
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setTitle(getString(R.string.settings))
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
                                        Toast.makeText(context, getString(R.string.successfully_updated_profile_photo), Toast.LENGTH_LONG).show()
                                        viewModel.requestGetUserDetail(getUserID())
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
                            is Result.Loading -> {}

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
                                            }
                                        }
                                    } else {
                                        showAlertDialogTheme(title = getString(R.string.error), contentMessage = it.body.message)
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
    private fun setupUI() {
        binding.apply {
            tvUserName.text = getUserName()
            llBuyTariff.setOnClickListener { startActivity(TariffsActivity.createIntent(requireContext())) }
            llUpdateUserInfo.setOnClickListener { resultUserDetail.launch(UpdateUserInfoActivity.createIntent(requireContext())) }
            llMyTickets.setOnClickListener { startActivity(TicketsActivity.createIntent(requireContext(), getUserID())) }
            llMyComments.setOnClickListener { startActivity(MyCommentsActivity.createIntent(requireContext())) }
            llMyTariffs.setOnClickListener { startActivity(MyTariffsActivity.createIntent(requireContext())) }
            llBlockedUsers.setOnClickListener { startActivity(BlockedUsersActivity.createIntent(requireContext())) }
            llSuggestionAndComplaint.setOnClickListener { startActivity(SuggestionAndComplaintActivity.createIntent(requireContext())) }
            llSignOut.setOnClickListener {
                clearUserName()
                clearPassword()
                clearMail()
                clearUserID()
                navigateToLogin()
            }
            llDeleteAccount.setOnClickListener { startActivity(UserDeleteActivity.createIntent(requireContext())) }

            ivProfile.setOnClickListener { showOptionDialog() }
            ivEditProfilePhoto.setOnClickListener { showOptionDialog() }
            tvVersion.text = "Versiyon ${requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0).versionName}"
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
        ).show(requireActivity())
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
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_LONG).show()
            }
        }
    }

    private val resultUserDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.requestGetUserDetail(getUserID())
        }
    }

    private fun updateProfilePhoto(uri: Uri) {
        with(viewModel) {
            updateProfilePhoto(uri)
            viewModel.requestUpdateUserInfo(
                UpdateUserInfoRequest(getUserID(), convertToBase64(uri.path!!), null, null, null, true)
            )
        }
    }

    private fun backToMainMenu() {
        (activity as MainActivity).apply {
            showFragment(homeFragment)
            setItemInNavigation(homeFragment)
        }
    }

    private fun navigateToLogin() = startActivity(LoginActivity.createIntent(requireContext()))

}