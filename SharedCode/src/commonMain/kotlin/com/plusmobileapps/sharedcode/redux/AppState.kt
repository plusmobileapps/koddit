package com.plusmobileapps.sharedcode.redux

import org.reduxkotlin.Reducer
import org.reduxkotlin.Store
import org.reduxkotlin.createThreadSafeStore

data class AppState(val count: Int = 0)

class IncrementCount

val reducer: Reducer<AppState> = { state, action ->
    when (action) {
        is IncrementCount -> state.copy(count = state.count + 1)
        else -> state
    }
}


val store: Store<AppState> = createThreadSafeStore(reducer, AppState())


/**
 * for ios only
 */
fun getStore(): Store<AppState> = store
//TODO fix in ios