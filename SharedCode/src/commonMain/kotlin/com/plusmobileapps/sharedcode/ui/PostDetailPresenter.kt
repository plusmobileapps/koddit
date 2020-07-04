package com.plusmobileapps.sharedcode.ui

import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.redux.AppState
import com.plusmobileapps.sharedcode.redux.presentermiddleware.ViewWithProvider

interface PostDetailView: ViewWithProvider<AppState> {
    fun showPost(post: RedditPostResponse)

    override fun presenter() = postDetailPresenter
}

val postDetailPresenter = presenter<PostDetailView> {
    {
        select { state.postDetail } then {
            showPost(state.postDetail)
        }
    }
}