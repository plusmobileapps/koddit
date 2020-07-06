package com.plusmobileapps.koddit

import android.app.Application
import com.plusmobileapps.sharedcode.context
import com.plusmobileapps.sharedcode.redux.reduxStore

class KodditApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        reduxStore.dispatch("Application Started")
    }
}