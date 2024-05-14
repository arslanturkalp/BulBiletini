package com.alparslanturk.bulbiletini.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun DialogFragment.show(activity: FragmentActivity) {
    show(activity.supportFragmentManager, this::class.java.canonicalName)
}

fun BottomSheetDialogFragment.show(activity: FragmentActivity) {
    show(activity.supportFragmentManager, this::class.java.canonicalName)
}

fun BottomSheetDialogFragment.showWithCondition(activity: FragmentActivity, predicate: () -> Boolean) {
    if (predicate.invoke()) {
        show(activity.supportFragmentManager, this::class.java.canonicalName)
    }
}