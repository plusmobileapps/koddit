package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.RedditPostResponse
import org.reduxkotlin.*

data class AppState(
    val isLoading: Boolean = false,
    val posts: List<RedditPostResponse> = listOf(),
    val error: String = ""
) {
    companion object {
        val initialState = AppState(isLoading = true)
    }
}

class Actions {
    object LoadingFeed
    data class FeedLoaded(val posts: List<RedditPostResponse>)
    data class FeedError(val message: String)
}

object ApplicationStarted
object FeedStarted
data class UpVoteAction(val postId: String)
data class PostDetailAction(val postId: String)
data class DownVoteAction(val postId: String)
data class MoreOptionsAction(val postId: String)
data class OpenCommentAction(val postId: String)
data class SharePostAction(val postId: String)

fun rootReducer(state: AppState, action: Any): AppState {
    return when (action) {
        is ActionTypes.INIT -> state
        is ApplicationStarted -> state
        is Actions.LoadingFeed -> AppState(isLoading = true, posts = listOf())
        is Actions.FeedLoaded -> AppState(isLoading = false, posts = action.posts)
        is Actions.FeedError -> AppState(isLoading = false, posts = emptyList())
        is UpVoteAction -> {
            state.copy(posts = state.posts.map { post ->
                if (post.id == action.postId) {
                    post.copy(ups = post.ups + 1)
                } else {
                    post
                }
            })
        }
        else -> state
    }
}
