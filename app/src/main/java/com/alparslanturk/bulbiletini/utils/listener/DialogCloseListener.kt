package com.alparslanturk.bulbiletini.utils.listener

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface DialogCloseListener {
    fun dialogClosed(fragment: BottomSheetDialogFragment, data: Bundle? = null)
}