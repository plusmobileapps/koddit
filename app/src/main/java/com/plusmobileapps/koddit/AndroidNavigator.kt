package com.plusmobileapps.koddit

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.plusmobileapps.sharedcode.redux.Navigator
import com.plusmobileapps.sharedcode.redux.Screen
import java.lang.Exception

class AndroidNavigator : Navigator, Application.ActivityLifecycleCallbacks {


    private var currentActivity: AppCompatActivity? = null
    private var cachedNavigationScreen: Screen? = null

    //TODO consider using current screen & destination screen to determine routing & animation
    override fun goto(screen: Screen) {
        if (currentActivity == null) {
            cachedNavigationScreen = screen
        } else {
            val navController = currentActivity!!.findNavController(R.id.nav_host_fragment)
            when (screen) {
                Screen.FEED -> navController.navigate(R.id.FirstFragment)
                Screen.POST_DETAIL -> navController.navigate(R.id.feed_to_post_detail)
//                Screen.SETTINGS -> {
//                    val dialog = SettingsDialogFragment.newInstance()
//                    dialog.show(currentActivity!!.supportFragmentManager, "SettingsFragment")
//                }
                else -> throw IllegalArgumentException("Screen $screen is not handled in AndroidNavigator")
            }
        }
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
        if (cachedNavigationScreen != null) {
            goto(cachedNavigationScreen!!)
            cachedNavigationScreen = null
        }
        attachActivity(activity)
    }

    private fun attachActivity(activity: Activity?) {
        try {
            currentActivity = activity as AppCompatActivity?
        } catch (e: Exception) {
            Log.d("AndroidNavigator", "Exception casting activity to AppCompatActivity.  $e")
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        attachActivity(activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        if (activity == currentActivity) {
            currentActivity = null
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }
}