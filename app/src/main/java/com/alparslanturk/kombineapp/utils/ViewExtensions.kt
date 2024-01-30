package com.alparslanturk.kombineapp.utils

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.addOnBackPressedListener(onBackPressed: () -> Unit) = onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        onBackPressed.invoke()
    }
})