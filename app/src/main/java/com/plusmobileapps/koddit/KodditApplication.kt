package com.plusmobileapps.koddit

import android.app.Application
import com.plusmobileapps.sharedcode.context
import com.plusmobileapps.sharedcode.redux.ApplicationStarted
import com.plusmobileapps.sharedcode.redux.ReduxEngine
import com.plusmobileapps.sharedcode.startLogging
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.Dispatcher

lateinit var dispatch: Dispatcher

class KodditApplication : Application() {

    companion object {
        lateinit var reduxEngine: ReduxEngine
    }

    override fun onCreate() {
        super.onCreate()
        startLogging()
        context = this
        val navigator = AndroidNavigator()
        reduxEngine = ReduxEngine(navigator, Dispatchers.Main, Dispatchers.IO)
        dispatch = reduxEngine.store.dispatch
        registerActivityLifecycleCallbacks(navigator)
        dispatch(ApplicationStarted)
    }
}