package com.alparslanturk.bulbiletini.ui.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.alparslanturk.bulbiletini.ui.generic.GenericProgressDialog
import com.alparslanturk.bulbiletini.utils.FirebaseUtils

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: GenericProgressDialog? = null

    lateinit var mFirebaseUtils: FirebaseUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdge()

        mFirebaseUtils = FirebaseUtils(this)
    }

    @Suppress("DEPRECATION")
    private fun setEdgeToEdge() {
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                left = insets.left,
                right = insets.right,
                bottom = insets.bottom,
            )
            WindowInsetsCompat.CONSUMED
        }
        ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars = false
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