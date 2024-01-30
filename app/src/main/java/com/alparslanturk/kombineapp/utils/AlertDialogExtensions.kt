package com.alparslanturk.kombineapp.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.alparslanturk.kombineapp.R

fun Fragment.showAlertDialogTheme(
    title: String? = requireContext().getString(R.string.app_name),
    contentMessage: String?,
    isCancelable: Boolean = false,
    showPositiveButton: Boolean = true,
    positiveButtonTitle: String? = requireContext().getString(R.string.done),
    onPositiveButtonClick: (() -> Unit?)? = null,
    showNegativeButton: Boolean = false,
    negativeButtonTitle: String? = requireContext().getString(R.string.cancel),
    onNegativeButtonClick: (() -> Unit?)? = null,
    showNeutralButton: Boolean = false,
    neutralButtonTitle: String? = requireContext().getString(R.string.cancel),
    onNeutralButtonClick: (() -> Unit?)? = null
) {
    AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme).apply {
        setTitle(title)
        setMessage(contentMessage)
        setCancelable(isCancelable)

        if (showPositiveButton) {
            setPositiveButton(positiveButtonTitle) { _, _ -> onPositiveButtonClick?.invoke() }
        }

        if (showNegativeButton) {
            setNegativeButton(negativeButtonTitle) { _, _ -> onNegativeButtonClick?.invoke() }
        }

        if (showNeutralButton) {
            setNeutralButton(neutralButtonTitle) { _, _ -> onNeutralButtonClick?.invoke() }
        }

    }.show()
}

fun Activity.showAlertDialogTheme(
    title: String? = this.getString(R.string.app_name),
    contentMessage: String?,
    isCancelable: Boolean = false,
    showPositiveButton: Boolean = true,
    positiveButtonTitle: String? = this.getString(R.string.done),
    onPositiveButtonClick: (() -> Any)? = null,
    showNegativeButton: Boolean = false,
    negativeButtonTitle: String? = this.getString(R.string.cancel),
    onNegativeButtonClick: (() -> Unit?)? = null,
    showNeutralButton: Boolean = false,
    neutralButtonTitle: String? = this.getString(R.string.cancel),
    onNeutralButtonClick: (() -> Unit?)? = null
) {
    AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
        setTitle(title)
        setMessage(contentMessage)
        setCancelable(isCancelable)

        if (showPositiveButton) {
            setPositiveButton(positiveButtonTitle) { _, _ -> onPositiveButtonClick?.invoke() }
        }

        if (showNegativeButton) {
            setNegativeButton(negativeButtonTitle) { _, _ -> onNegativeButtonClick?.invoke() }
        }

        if (showNeutralButton) {
            setNeutralButton(neutralButtonTitle) { _, _ -> onNeutralButtonClick?.invoke() }
        }

    }.show()
}