package com.plusmobileapps.sharedcode.redux

import com.plusmobileapps.sharedcode.RedditPostResponse
import com.plusmobileapps.sharedcode.redux.presentermiddleware.Presenter
import com.plusmobileapps.sharedcode.redux.presentermiddleware.View
import com.plusmobileapps.sharedcode.redux.presentermiddleware.ViewWithProvider
import com.plusmobileapps.sharedcode.redux.presentermiddleware.createGenericPresenter
import com.plusmobileapps.sharedcode.ui.presenter

interface HomeFeedView: ViewWithProvider<AppState> {
    fun showLoading(show: Boolean)
    fun showError(error: String)
    fun hideError()
    fun showPosts(posts: List<RedditPostResponse>)

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
    }
}