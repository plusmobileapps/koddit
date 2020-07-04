package com.plusmobileapps.sharedcode.ui

import com.plusmobileapps.sharedcode.redux.*
import com.plusmobileapps.sharedcode.redux.NavigationActions
import com.plusmobileapps.sharedcode.ui.UiActions.DownVoteAction
import com.plusmobileapps.sharedcode.ui.UiActions.UpVoteAction
import org.reduxkotlin.Store
import org.reduxkotlin.middleware

fun uiMiddleware(homeFeedThunk: HomeFeedThunk) = middleware<AppState> { store, next, action ->
    when(action) {
        is UpVoteAction -> store.dispatch(homeFeedThunk.upVote(action.postId))
        is DownVoteAction -> store.dispatch(homeFeedThunk.downVote(action.postId))
        is FeedStarted -> store.dispatch(homeFeedThunk.fetchDankMemes())
        is UiActions.PostDetailAction -> {
//            store.dispatch(Actions.PostDetailOpened(action.post))
            store.dispatch(NavigationActions.NavigateTo(Screen.POST_DETAIL))
            next(action)
        }
        else -> next(action)
    }
}