package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.ApplicationDispatcher
import com.plusmobileapps.sharedcode.redux.presentermiddleware.createThunkMiddleware
import com.plusmobileapps.sharedcode.redux.presentermiddleware.presenterEnhancer
import com.plusmobileapps.sharedcode.ui.uiMiddleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import kotlin.coroutines.CoroutineContext

class ReduxEngine(
    navigator: Navigator,
    private val uiContext: CoroutineContext,
    private val backgroundContext: CoroutineContext
) {
    val store = createThreadSafeStore(
        ::rootReducer,
        AppState.initialState,
        compose(
            listOf(
                presenterEnhancer(uiContext),
                applyMiddleware(
                    createThunkMiddleware(),
                    uiMiddleware(HomeFeedThunk(ApplicationDispatcher)),
                    navigationMiddleware(navigator),
                    loggingMiddleware
                )
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