package com.plusmobileapps.koddit

import android.app.Application
import com.plusmobileapps.sharedcode.context
import com.plusmobileapps.sharedcode.redux.ApplicationStarted
import com.plusmobileapps.sharedcode.redux.store

class KodditApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        store.dispatch(ApplicationStarted)
    }
}