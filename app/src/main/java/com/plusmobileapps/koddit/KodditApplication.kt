package com.plusmobileapps.koddit

import android.app.Application
import com.plusmobileapps.sharedcode.context
import com.plusmobileapps.sharedcode.di.commonModule
import com.plusmobileapps.sharedcode.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class KodditApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        initKoin {
            androidLogger()
            androidContext(this@KodditApplication)
            modules(commonModule)
        }
    }
}