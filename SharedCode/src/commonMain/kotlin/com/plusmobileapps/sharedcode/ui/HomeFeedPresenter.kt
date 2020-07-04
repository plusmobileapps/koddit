package com.plusmobileapps.sharedcode.ui

import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.redux.AppState
import com.plusmobileapps.sharedcode.redux.presentermiddleware.ViewWithProvider

interface HomeFeedView: ViewWithProvider<AppState> {
    fun showLoading(show: Boolean)
    fun showError(error: String)
    fun hideError()
    fun showPosts(posts: List<RedditPostResponse>)
    fun showShareSheet(url: String)

    override fun presenter() = startPresenter
}

val startPresenter = presenter<HomeFeedView> {
    {
        select { state.isLoading } then {
            showLoading(state.isLoading)
        }

        select { state.error } then {
            if (state.error.isNotEmpty()){
                showError(state.error)
            } else {
                hideError()
            }
        }
        select { state.posts } then  {
            showPosts(state.posts)
        }

        select { state.shareUrl } then {
            if (state.shareUrl.isNotEmpty()) {
                showShareSheet(state.shareUrl)
            }
        }
    }
}