package com.plusmobileapps.koddit

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.plusmobileapps.sharedcode.PresenterLifecycleObserver
import com.plusmobileapps.sharedcode.ui.BaseView

open class BaseFragment<V: BaseView>: Fragment(), BaseView {
    //https://stackoverflow.com/a/55955286
    private val presenterObserver = PresenterLifecycleObserver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.addObserver(presenterObserver)
        super.onCreate(savedInstanceState)
    }
}