package com.plusmobileapps.sharedcode

import com.github.aakira.napier.DebugAntilog
import com.github.aakira.napier.Napier

fun startLogging() {
    Napier.base(DebugAntilog())
}