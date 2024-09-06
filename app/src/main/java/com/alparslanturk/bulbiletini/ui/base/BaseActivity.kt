package com.alparslanturk.bulbiletini.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alparslanturk.bulbiletini.ui.generic.GenericProgressDialog
import com.alparslanturk.bulbiletini.utils.FirebaseUtils

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: GenericProgressDialog? = null

    lateinit var mFirebaseUtils: FirebaseUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseUtils = FirebaseUtils(this)
    }

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