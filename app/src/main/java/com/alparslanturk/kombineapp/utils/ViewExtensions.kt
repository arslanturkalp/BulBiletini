package com.alparslanturk.kombineapp.utils

import android.graphics.Paint
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomnavigation.BottomNavigationView

fun AppCompatActivity.addOnBackPressedListener(onBackPressed: () -> Unit) = onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
    override fun handleOnBackPressed() {
        onBackPressed.invoke()
    }
})

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun AppCompatTextView.setTextUnderLine() {
    this.paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun BottomNavigationView.setSelectedTab(tabId: Int) {
    this.findViewById<View>(tabId).performClick()
}