package com.manu.investodroid

import android.app.Application
import com.facebook.stetho.Stetho

class InvestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}