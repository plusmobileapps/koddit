package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.middleware.loggingMiddleware
import org.reduxkotlin.*

data class AppState(val count: Int = 0)

class IncrementCount

val reducer: Reducer<AppState> = { state, action ->
    when (action) {
        is IncrementCount -> state.copy(count = state.count + 1)
        else -> state
    }
}


val reduxStore: Store<AppState> = createThreadSafeStore(reducer, AppState(), applyMiddleware(loggingMiddleware))


/**
 * for ios only
 */
fun getStore(): Store<AppState> = reduxStore
