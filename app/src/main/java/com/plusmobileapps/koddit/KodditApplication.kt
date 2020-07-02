package com.plusmobileapps.koddit

import android.app.Application
import com.plusmobileapps.sharedcode.context
import com.plusmobileapps.sharedcode.redux.ApplicationStarted
import com.plusmobileapps.sharedcode.redux.ReduxEngine
import kotlinx.coroutines.Dispatchers
import org.reduxkotlin.Dispatcher

lateinit var dispatch: Dispatcher

class KodditApplication : Application() {

    companion object {
        lateinit var reduxEngine: ReduxEngine
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        reduxEngine = ReduxEngine(Dispatchers.Main)
        dispatch = reduxEngine.store.dispatch
        dispatch(ApplicationStarted)
    }
}