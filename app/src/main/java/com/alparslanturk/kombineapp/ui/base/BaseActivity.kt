package com.alparslanturk.kombineapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alparslanturk.kombineapp.ui.generic.GenericProgressDialog

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: GenericProgressDialog? = null

    fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = GenericProgressDialog()
            progressDialog?.show(supportFragmentManager, "ProgressDialog")
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog?.dismissAllowingStateLoss()
            progressDialog = null
        }
    }
}