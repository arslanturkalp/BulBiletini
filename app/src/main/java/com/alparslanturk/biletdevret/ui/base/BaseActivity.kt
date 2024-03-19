package com.alparslanturk.biletdevret.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.alparslanturk.biletdevret.ui.generic.GenericProgressDialog

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