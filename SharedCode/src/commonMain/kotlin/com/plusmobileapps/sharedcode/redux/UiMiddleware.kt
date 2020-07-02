package com.plusmobileapps.sharedcode.redux

import org.reduxkotlin.middleware

fun uiMiddleware(homeFeedThunk: HomeFeedThunk) = middleware<AppState> { store, next, action ->
    when(action) {
        is FeedStarted -> store.dispatch(homeFeedThunk.fetchDankMemes())
        else -> next(action)
    }

}