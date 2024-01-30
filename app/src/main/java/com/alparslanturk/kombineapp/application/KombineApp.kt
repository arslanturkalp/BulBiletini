package com.alparslanturk.kombineapp.application

import android.app.Application
import android.content.Context
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KombineApp : Application() {

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
        var instance: KombineApp? = null
            private set

    }

}