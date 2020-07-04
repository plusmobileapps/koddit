package com.plusmobileapps.sharedcode.ui

import com.plusmobileapps.sharedcode.RedditPostResponse

sealed class UiActions {
    data class UpVoteAction(val postId: String) : UiActions()
    data class PostDetailAction(val post: RedditPostResponse) : UiActions()
    data class DownVoteAction(val postId: String) : UiActions()
    data class MoreOptionsAction(val postId: String) : UiActions()
    data class OpenCommentAction(val postId: String) : UiActions()
    data class SharePostAction(val post: RedditPostResponse) : UiActions()
}