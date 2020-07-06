package com.plusmobileapps.sharedcode.middleware

import com.github.aakira.napier.Napier
import com.plusmobileapps.sharedcode.redux.AppState
import org.reduxkotlin.middleware

internal val loggingMiddleware = middleware<AppState> { _, next, action ->
    val result = next(action)
    Napier.d("DISPATCH action: ${action::class.simpleName}: $action")
    result
}