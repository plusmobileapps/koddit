package com.plusmobileapps.sharedcode.redux

import com.github.aakira.napier.Napier
import com.plusmobileapps.sharedcode.FeedRepository
import com.plusmobileapps.sharedcode.FeedResult
import com.plusmobileapps.sharedcode.di.Di
import com.plusmobileapps.sharedcode.di.commonModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.direct
import org.kodein.di.instance
import org.reduxkotlin.Thunk
import kotlin.coroutines.CoroutineContext

class HomeFeedThunk(
    networkContext: CoroutineContext,
    private val feedRepository: FeedRepository = commonModule.direct.instance()
) {
    private val networkScope = CoroutineScope(networkContext)

    fun fetchDankMemes(): Thunk<AppState> = { dispatch, _, _ ->
        networkScope.launch {
            dispatch(Actions.LoadingFeed)
            val feedResult = feedRepository.getDankMemes()
            when(feedResult) {
                is FeedResult.Success -> dispatch(Actions.FeedLoaded(feedResult.posts))
                is FeedResult.Error -> dispatch(Actions.FeedError(feedResult.error))
            }
        }
    }

    fun upVote(postId: String): Thunk<AppState> = { dispatch, getState, extraArg ->
        if (!getState().isAuthenticated) {
            dispatch(Actions.PromptAuthentication)
        } else {
            networkScope.launch {
                val result = feedRepository.upVote(postId)
                if (result) {
                    val newList = getState().posts.map {
                        if (it.id == postId) {
                            it.copy(ups = it.ups + 1)
                        } else {
                            it
                        }
                    }
                    dispatch(Actions.FeedLoaded(newList))
                }
            }
        }

    }

    fun downVote(postId: String): Thunk<AppState> = {dispatch, getState, extraArg ->

    }
}