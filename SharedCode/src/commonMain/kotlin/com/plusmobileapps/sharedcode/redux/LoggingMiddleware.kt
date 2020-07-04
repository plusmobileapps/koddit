package com.plusmobileapps.sharedcode.redux

import com.github.aakira.napier.Napier
import org.reduxkotlin.middleware

internal val loggingMiddleware = middleware<AppState> { store, next, action ->
    val result = next(action)
    Napier.d("DISPATCH action: ${action::class.simpleName}: $action")
    result
}