package com.alparslanturk.bulbiletini.utils

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log

@Suppress("DEPRECATION", "UNCHECKED_CAST")
fun <T> Intent.getDataExtra(key: String): T {
    val data = this.extras?.get(key)
    return try {
        data as T
    } catch (e: Exception) {
        Log.e("EXTRAS", "----- Wrong Cast Exception -----")
        null!!
    }
}

@Suppress("DEPRECATION")
inline fun <reified T> Bundle.getParcelableDataExtra(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> getParcelable(key) as? T
}