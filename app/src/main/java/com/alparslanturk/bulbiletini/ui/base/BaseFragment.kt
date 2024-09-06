package com.alparslanturk.bulbiletini.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alparslanturk.bulbiletini.ui.generic.GenericProgressDialog
import com.alparslanturk.bulbiletini.utils.FirebaseUtils

open class BaseFragment : Fragment() {

    private var progressDialog: GenericProgressDialog? = null

    lateinit var mFirebaseUtils: FirebaseUtils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFirebaseUtils = FirebaseUtils(context)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showProgressDialog() {
        progressDialog = GenericProgressDialog()
        progressDialog?.show(childFragmentManager, "ProgressDialog")
    }

    fun dismissProgressDialog() {
        progressDialog?.dismissAllowingStateLoss()
    }

}