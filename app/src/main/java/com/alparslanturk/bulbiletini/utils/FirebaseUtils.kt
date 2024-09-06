package com.alparslanturk.bulbiletini.utils

import android.content.Context
import android.os.Bundle
import com.alparslanturk.bulbiletini.application.SessionManager.getDeviceId
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseUtils(context: Context?) {

    private var firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context!!)

    fun logScreenViewEvent(screenName: String?, className: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, className)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun setFirebaseUserId() {
        firebaseAnalytics.setUserId(getDeviceId())
        FirebaseCrashlytics.getInstance().setUserId(getDeviceId())
    }

}