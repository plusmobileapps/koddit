package com.plusmobileapps.koddit

import android.app.Application
import com.plusmobileapps.sharedcode.context

class KodditApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}