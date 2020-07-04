package com.plusmobileapps.sharedcode.redux

import com.github.aakira.napier.Napier
import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.shareLink
import com.plusmobileapps.sharedcode.ui.UiActions
import org.reduxkotlin.*

data class AppState(
    val isAuthenticated: Boolean = true,
    val isLoading: Boolean = false,
    val posts: List<RedditPostResponse> = listOf(),
    val shareUrl: String = "",
    val error: String = "",
    val promptAuthentication: Boolean = false,
    val postDetail: RedditPostResponse = RedditPostResponse()
) {
    companion object {
        val initialState = AppState(isLoading = true)
    }
}

class Actions {
    object LoadingFeed
    data class FeedLoaded(val posts: List<RedditPostResponse>)
    data class FeedError(val message: String)
    object PromptAuthentication
    object AuthenticationDialogDismissed
    object PostDetailClosed
    object ShareSheetShown
}

object ApplicationStarted
object FeedStarted



fun rootReducer(state: AppState, action: Any): AppState {
    return when (action) {
        is ActionTypes.INIT -> state
        is ApplicationStarted -> state
        is Actions.LoadingFeed -> AppState(isLoading = true, posts = listOf())
        is Actions.FeedLoaded -> AppState(isLoading = false, posts = action.posts)
        is Actions.FeedError -> AppState(isLoading = false, posts = emptyList())
        is Actions.ShareSheetShown -> state.copy(shareUrl = "")
        is UiActions.SharePostAction -> state.copy(shareUrl = action.post.shareLink)
        is UiActions.PostDetailAction -> state.copy(postDetail = action.post).also { Napier.d("${action.post}", null, "Post clickedd") }
        is Actions.PostDetailClosed -> state.copy(postDetail = RedditPostResponse())
        is Actions.PromptAuthentication -> state.copy(promptAuthentication = true)
        is Actions.AuthenticationDialogDismissed -> state.copy(promptAuthentication = false)
        else -> state
    }
}
