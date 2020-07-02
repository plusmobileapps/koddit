package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.redux.presentermiddleware.presenterEnhancer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware
import kotlin.coroutines.CoroutineContext

class ReduxEngine(
    private val uiContext: CoroutineContext
) {
    val store = createThreadSafeStore(
        ::rootReducer,
        AppState.initialState,
        compose(
            listOf(
                presenterEnhancer(uiContext),
                applyMiddleware(createThunkMiddleware())
            )
        )
    )

    fun dispatch(action: Any) {
        CoroutineScope(uiContext).launch {
            store.dispatch(action)
        }
    }

    val appState: AppState get() = store.state
}