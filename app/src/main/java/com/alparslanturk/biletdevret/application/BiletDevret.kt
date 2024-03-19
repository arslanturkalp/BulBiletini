package com.alparslanturk.biletdevret.application

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BiletDevret : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Hawk.init(instance).build()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {
        val TAG: String = Application::class.java.simpleName

        @get:Synchronized
        var instance: BiletDevret? = null
            private set

    }

}